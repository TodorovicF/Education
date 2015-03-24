<%@ page import="bookstore.data.ProductDB" %>
<%@ page import="bookstore.business.Product" %>
<%@ page import="java.util.List" %>
<jsp:include page="/includes/header.jsp" />
<jsp:useBean id="prod" class="bookstore.business.Product"/>
<section>
    <h1>BookStore Catalog</h1>

<%
    List<Product> productList = ProductDB.selectProducts();
    if(productList != null && productList.size() > 0) {
        String currentCategory = "";
        for (Product p : productList) {
            if(!p.getCategory().equals(currentCategory)) {
                currentCategory = p.getCategory();
                %>
                    <h2><%= currentCategory%></h2>
                <%
            }
            %>
                <p><a href="/catalog/book.jsp?ISBN13=<%= p.getISBN13()%>"><%= p.getDescription() %></a></p>
            <%
        }
    } else {
        %>
        <p>Store has no Products</p>
        <%
    }
%>
</section>
<jsp:include page="/includes/footer.jsp" />
