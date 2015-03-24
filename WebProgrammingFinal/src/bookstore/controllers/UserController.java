package bookstore.controllers;

import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;

import bookstore.business.Invoice;
import bookstore.business.Product;
import bookstore.business.User;
import bookstore.data.InvoiceDB;
import bookstore.data.ResultsDB;
import bookstore.data.UserDB;

@WebServlet(name = "UserController", urlPatterns = {"/user/*"})
public class UserController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/deleteCookies")) {
            url = deleteCookies(request, response);
        } else if (requestURI.endsWith("/searchResults")) {
            url = searchResults(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }



    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/subscribeToEmail")) {
            url = subscribeToEmail(request, response);
        } else if (requestURI.endsWith("/userLogin")) {
            url = userLogin(request, response);
        } else if (requestURI.endsWith("/userRegister")) {
            url = userRegister(request, response);
        } else if (requestURI.endsWith("/updateUser")){
            url = updateUser(request, response);
        } else if (requestURI.endsWith("/orderHistory")){
            url = orderHistory(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String orderHistory(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user == null) {
            return "/login.jsp";
        }
        List<Invoice> invoiceList = InvoiceDB.selectUserInvoices(user);
        session.setAttribute("userInvoices",invoiceList);

        return "/orderHistory.jsp";
    }

    private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);  //delete the cookie
            cookie.setPath("/");  //entire application
            response.addCookie(cookie);
        }
        return "/delete_cookies.jsp";
    }

    private String subscribeToEmail(HttpServletRequest request,
            HttpServletResponse response) {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        request.setAttribute("user", user);

        String url;
        String message;
        //check that email address doesn't already exist
        if (UserDB.emailExists(email)) {
            message = "This email address already exists. <br>"
                    + "Please enter another email address.";
            request.setAttribute("message", message);
            url = "/email/index.jsp";
        } else {
            UserDB.insert(user);
            message = "";
            request.setAttribute("message", message);
            url = "/email/thanks.jsp";
        }
        return url;
    }

    private String userLogin(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer url = request.getRequestURL();
        String email= request.getParameter("email");
        String password= request.getParameter("password");
        UserDB dbConn = new UserDB();

        User user = dbConn.selectUser(email);
        if(user != null) {
            if(user.getPasswd().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user",user);
                Cookie cookie = new Cookie("userLoggedIn", "True");
                cookie.setMaxAge(60 * 60); //1 hour
                cookie.setPath("/");
                response.addCookie(cookie);
                cookie = new Cookie("emailCookie",email);
                cookie.setMaxAge(60 * 60); //1 hour
                cookie.setPath("/");
                response.addCookie(cookie);
                return "/index.jsp";
            }
        }

        return "/login_error.jsp";
    }

    private String userRegister(HttpServletRequest request, HttpServletResponse response) {
        String url;
        String password= request.getParameter("passwd");
        String name= request.getParameter("name");
        String email= request.getParameter("email");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
        }
        user.setPasswd(password);
        user.setFirstName(name);
        user.setEmail(email);
        UserDB dbConn = new UserDB();

        if(dbConn.emailExists(email)) {
            url = "/registerError.jsp";
            return url;
        }

        dbConn.insert(user);

        if(dbConn.emailExists(email)){
            Cookie cookie = new Cookie("userLoggedIn","True");
            cookie.setMaxAge(60*60); //1 hour
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("emailCookie",email);
            cookie.setMaxAge(60 * 60); //1 hour
            cookie.setPath("/");
            response.addCookie(cookie);
            url = "/index.jsp";
            session.setAttribute("user", user);
        }else{
            url = "/registerError.jsp";
        }

        return url;
    }

    private String searchResults(HttpServletRequest request, HttpServletResponse response) {
        String url = "/searchResults.jsp";

        String search = request.getParameter("search");
        String context = request.getParameter("context");
        Boolean validSearch = true;
        if (search.length() <= 2) validSearch = false;

        List<Product> results = ResultsDB.searchResults(search,context);

        HttpSession session = request.getSession();
        session.setAttribute("searchResults", results);
        session.setAttribute("validSearch", validSearch);
        return url;
    }

    private String updateUser(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        String currentPassword = request.getParameter("currentPasswd");
        String newPassword = request.getParameter("newPasswd");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
        }

        if (UserDB.emailExists(email)) {
            user = UserDB.selectUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAddress1(address1);
            user.setAddress2(address2);
            user.setCity(city);
            user.setState(state);
            user.setZip(zip);
            user.setCountry(country);
            if(!newPassword.equals("")) {
                if(user.getPasswd().equals(currentPassword)) {
                    user.setPasswd(newPassword);
                }
            }
            UserDB.update(user);
        }

        session.setAttribute("user", user);
        return "/userProfile.jsp";
    }
}
