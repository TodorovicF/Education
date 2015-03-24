package bookstore.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import bookstore.business.*;
import bookstore.data.*;
import bookstore.util.CookieUtil;

@WebServlet(name = "CatalogController", urlPatterns = {"/catalog/product/*"})
public class CatalogController extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String url = showProduct(request, response);

        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String url = "/catalog";
        if (requestURI.endsWith("/register")) {
            url = registerUser(request, response);
        }
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
    
    private String showProduct(HttpServletRequest request, 
            HttpServletResponse response) {
        String productCode = request.getPathInfo();
        // This should never be null. But just to be safe.
        if (productCode != null) {
            productCode = productCode.substring(1);
            Product product = ProductDB.selectProduct(productCode);
            if(product == null) {
                return "/catalog/index.jsp";
            }
            HttpSession session = request.getSession();
            session.setAttribute("product", product);
        }        
        return "/book.jsp?ISBN13=" + productCode;
    }
    
    private String registerUser(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user;
        if (UserDB.emailExists(email)) {
            user = UserDB.selectUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);            
            UserDB.update(user);
        } else {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);            
            UserDB.insert(user);
        }

        session.setAttribute("user", user);

        Cookie emailCookie = new Cookie("emailCookie", email);
        emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);
        
        Product product = (Product) session.getAttribute("product");
        String url = "/catalog/" + product.getISBN13() + "/sound.jsp";
        return url;
    }    
}
