<%@ page import="bookstore.business.Product" %>
<%@ page import="bookstore.data.MarketDataDB" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: v-ctran
  Date: 3/10/2015
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./includes/header.jsp"/>
<%
    List<Product> bestSellers = MarketDataDB.topSellers();
    List<Product> bestCategory = MarketDataDB.topSellerCategory();
%>

<section>
    <%
        if(bestSellers != null && bestSellers.size() > 0){
            %>
            <h3>Top Sellers</h3>
            <table>
                <th>
                    &nbsp;
                </th>
                <th>
                    Book
                </th>
            <%
            for(int i = 1; i < bestSellers.size() +1; i++) {
                %>
                <tr>
                    <td><%= i%></td>
                    <td><a href="/catalog/book.jsp?ISBN13=<%=bestSellers.get(i-1).getISBN13()%>"><%= bestSellers.get(i-1).getDescription()%></a> </td>
                </tr>
                <%
            }
            %>
            </table>
            <%

            %>
            <h3>Top Seller by Category</h3>
            <%
                String category = "";
                for(Product p : bestCategory) {
                    if(!p.getCategory().equals(category)) {
                        category = p.getCategory();
                        %>
                            <h4><%= p.getCategory()%></h4>
                            <a href="/catalog/book.jsp?ISBN13=<%= p.getISBN13()%>"><%= p.getDescription()%></a>
                        <%
                    }
                }
            %>
            <%
        }

    %>


</section>
<jsp:include page="./includes/footer.jsp"/>