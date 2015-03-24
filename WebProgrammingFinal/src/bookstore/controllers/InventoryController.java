package bookstore.controllers;

import bookstore.business.Product;
import bookstore.data.ProductDB;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Christopher on 3/8/2015.
 */
@WebServlet(name = "InventoryController", urlPatterns = {"/Inventory/*"})
public class InventoryController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String url;
        if (requestURI.endsWith("/updateProduct")) {
            url = updateProduct(request, response);
        } else if (requestURI.endsWith("/addProduct")) {
            url = addProduct(request, response);
        } else {
            url = "/admin/inventoryControl.jsp";
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String addProduct(HttpServletRequest request, HttpServletResponse response) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {

            ServletContext context = request.getServletContext();

            String filepath = context.getInitParameter("image-upload");

            DiskFileItemFactory factory = new DiskFileItemFactory();

            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List<FileItem> fileItemList = upload.parseRequest(request);
                Iterator<FileItem> fileItemIterator = fileItemList.iterator();
                long isbn13 = -1;
                long isbn10 = -1;
                String title = "";
                String author = "";
                String category = "";
                int stock = -1;
                double price = -1.0;

                Product product = new Product();

                while(fileItemIterator.hasNext()) {
                    FileItem fileItem = fileItemIterator.next();
                    if(fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();
                        String fieldValue = fileItem.getString();

                        if(fieldName.equals("ISBN13")) {
                            try {
                                isbn13 = Long.parseLong(fieldValue);
                            } catch (NumberFormatException e) {
                                return "/admin/inventoryControl.jsp";
                            }
                        } else if (fieldName.equals("ISBN10")) {
                            try {
                                isbn10 = Long.parseLong(fieldValue);
                            } catch (NumberFormatException e) {
                                return "/admin/inventoryControl.jsp";
                            }
                        } else if(fieldName.equals("title")) {
                            title = fieldValue;
                        } else if(fieldName.equals("author")) {
                            author = fieldValue;
                        } else if(fieldName.equals("category")) {
                            category = fieldValue;
                        } else if(fieldName.equals("stock")) {
                            stock = Integer.parseInt(fieldValue);
                        } else if(fieldName.equals("price")) {
                            try {
                                price = Double.parseDouble(fieldValue);
                            }catch (NumberFormatException e) {
                                return "/admin/inventoryControl.jsp";
                            }
                        }
                    } else {
                        String fileName = fileItem.getName();
                        String fieldName = fileItem.getString();
                        long fileSize = fileItem.getSize();

                        //Write file
                        File file = new File(filepath,isbn13 + ".jpg");
                        fileItem.write(file);
                    }
                }

                if(isbn13 > 0 && isbn10 > 0 && !title.equals("") && !author.equals("") && !category.equals("") && stock > 0 && price > 0.0) {
                    product.setISBN13(isbn13);
                    product.setISBN10(isbn10);
                    product.setDescription(title);
                    product.setAuthor(author);
                    product.setCategory(category);
                    product.setStock(stock);
                    product.setPrice(price);

                    ProductDB.addProduct(product);
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
                return "/admin/inventoryControl.jsp";
            } catch (IOException e) {
                e.printStackTrace();
                return "/admin/inventoryControl.jsp";
            } catch (Exception e) {
                e.printStackTrace();
                return "/admin/inventoryControl.jsp";
            }
        }

        return "/admin/inventoryControl.jsp";
    }


    private String updateProduct(HttpServletRequest request, HttpServletResponse response) {
        long isbn13 = 0;
        try {
            isbn13 = Long.parseLong(request.getParameter("ISBN13"));
        } catch (NumberFormatException e) {
            isbn13 = 0;
        }

        int stock = 0;
        try {
            stock = Integer.parseInt(request.getParameter("stock"));
        } catch (NumberFormatException e) {
            stock = 0;
        }

        if(stock <= 0) {
            stock = 0;
        }

        Product product = ProductDB.selectProduct(isbn13);

        if(product != null) {
            product.setStock(stock);
            ProductDB.updateStock(product);
        }
        return "/admin/inventoryControl.jsp";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
