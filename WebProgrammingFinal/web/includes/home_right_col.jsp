<%@ page import="java.util.List" %>
<%@ page import="bookstore.business.Product" %>
<%@ page import="bookstore.data.ProductDB" %>
<%--
  Created by IntelliJ IDEA.
  User: Christopher
  Date: 2/19/2015
  Time: 19:12 PM
  To change this template use File | Settings | File Templates.
--%>
<aside id="homeRightCol">
    <h5>New Inventory!</h5>
    <%
        List<Product> productList = ProductDB.selectNewestProducts();
        if(productList != null && productList.size() > 0) {
            for(Product p : productList)
            {
    %>
            <a href="/catalog/book.jsp?ISBN13=<%= p.getISBN13()%>"><img width="75" src="/images/<%=p.getISBN13()%>.jpg"/> </a>
    <%
            }
        }
    %>
</aside>
