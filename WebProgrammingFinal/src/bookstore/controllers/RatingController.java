package bookstore.controllers;

import bookstore.business.Rating;
import bookstore.business.User;
import bookstore.data.RatingDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Christopher on 3/9/2015.
 */
@WebServlet(name = "RatingController", urlPatterns = {"/rating/*"})
public class RatingController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/addRating")) {
            url = addRating(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String addRating(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        long isbn13 = 0;
        try {
            isbn13 = Long.parseLong(request.getParameter("ISBN13"));
        } catch (NumberFormatException e) {
            isbn13 = 0;
        }
        String opinion = request.getParameter("opinion");
        int score = Integer.parseInt(request.getParameter("score"));
        User user = (User)session.getAttribute("user");

        if(user == null) {
            return  "/login_error.jsp";
        } else {
            Rating r = new Rating();
            r.setIsbn13(isbn13);
            r.setScore(score);
            r.setUserEmail(user.getEmail());
            r.setOpinion(opinion);

            RatingDB.insert(r);
        }
        return "/catalog/book.jsp?ISBN13=" +isbn13;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
