<jsp:include page="/includes/header.jsp" />
<%@ page import="bookstore.business.Product" %>
<%@ page import="java.util.List" %>
<%
    HttpSession httpSession = request.getSession();
    List<Product> results = (List<Product>)httpSession.getAttribute("searchResults");
    Boolean isValid = (Boolean)httpSession.getAttribute("validSearch");
%>
<!-- begin middle column -->

<section>
    <%if (!isValid) {%>
    <h1>Invalid Search</h1>
    <%}else if(results == null) {%>
    <h1>No Results</h1>
    <%} else {%>
        <%for (Product product : results) {%>
            <p><a href="/catalog/book.jsp?ISBN13=<%= product.getISBN13()%>"><%= product.getDescription()%> by <%= product.getAuthor()%></a></p>
        <%}%>
    <%}%>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />