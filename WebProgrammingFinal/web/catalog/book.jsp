<%@ page import="bookstore.data.ProductDB" %>
<%@ page import="bookstore.business.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="bookstore.business.Rating" %>
<%@ page import="bookstore.data.RatingDB" %>
<%@ page import="bookstore.util.CookieUtil" %>
<%@ page import="bookstore.business.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Christopher
  Date: 3/7/2015
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/includes/header.jsp"/>
<section>
<%
    long isbn13;
    try {
        isbn13 = Long.parseLong(request.getParameter("ISBN13"));
    } catch (NumberFormatException e) {
        isbn13 = 0;
    }
    Product product = ProductDB.selectProduct(isbn13);
    List<Rating> ratings = RatingDB.selectRatings(isbn13);
    List<Rating> bestRatings = RatingDB.bestRatings(isbn13);
    Cookie[] cookies = request.getCookies();
    String email = CookieUtil.getCookieValue(cookies, "emailCookie");
    User user = (User)session.getAttribute("user");

    if(product == null) {
    %>
        <h1>Book Not Found</h1>
    <%
    } else {
        %>
        <h1><%= product.getDescription()%></h1>
        <table>
            <tr>
                <td>
                    <img class="book-img" src="/images/<%= product.getISBN13()%>.jpg">
                </td>
                <td>
                    <dl>
                        <dt>
                            ISBN13
                        </dt>
                        <dd>
                            <%= product.getISBN13()%>
                        </dd>
                        <dt>
                            ISBN10
                        </dt>
                        <dd>
                            <%= product.getISBN10()%>
                        </dd>
                        <dt>
                            Author(s)
                        </dt>
                        <dd>
                            <%= product.getAuthor()%>
                        </dd>
                        <dt>
                            Stock Left
                        </dt>
                        <dd>
                            <%= product.getStock()%>
                        </dd>
                        <dt>
                            Cost
                        </dt>
                        <dd>
                            <%= product.getPriceCurrencyFormat()%>
                        </dd>
                    </dl>
                    <%
                        if(product.getStock() >0) {
                    %>
                    <form method="post" action="/order/addItem">
                        <input type="hidden" name="ISBN13" value="<%= product.getISBN13()%>">
                        <input type="image" src="/images/addToCart.jpg"
                               width="113" alt="Add to Cart">
                    </form>
                    <%
                        } else {
                    %>
                        <p><b>Out of Stock</b></p>
                    <%
                        }
                    %>
                </td>
            </tr>
        </table>
        <%
            if(ratings != null && ratings.size() >0) {
                int count = ratings.size();
                double sum = 0.0;
                for(Rating r : ratings) {
                    sum += r.getScore();

                }
                sum /= count;
                %>
                <h3>Rating of <%= String.format("%2.1f", sum)%>/10 from <%= count%> customer(s).</h3>
                <%
            } else {
        %>
            <h3>This book has no ratings</h3>
        <%
            }

            if(user  != null) {
        %>
                <form action="/rating/addRating" method="post">
                    <input type="hidden" name="ISBN13" value="<%= product.getISBN13()%>">
                    <input type="text" name="opinion" maxlength="250">
                    <select name="score" size="1">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                    <input type="submit" value="Rate!">
                </form>

                <%
                    if(bestRatings != null && bestRatings.size() > 0) {
                        %>
                        <table>
                            <th>
                                Rating
                            </th>
                            <th>
                                Comments
                            </th>
                        <%
                        for(Rating r : bestRatings) {
                            %>
                            <tr>
                                <td>
                                    <%= r.getScore()%>
                                </td>
                                <td>
                                    <%= r.getOpinion()%>
                                </td>
                            </tr>
                            <%
                        }
                    }
                %>
                        </table>
        <%
                } else {
                    %>
                    <a href="/register.jsp">Please Register to rate and view ratings for this book.</a>
    <%
                }
    }
%>
</section>
<jsp:include page="/includes/footer.jsp"/>
