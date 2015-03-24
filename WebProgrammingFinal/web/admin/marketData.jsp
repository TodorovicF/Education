<%@ page import="bookstore.business.UserCategory" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Christopher
  Date: 3/12/2015
  Time: 18:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/includes/header.jsp"/>
<%
    List<UserCategory> userCategories = (List<UserCategory>)session.getAttribute("marketData");
%>
<section>
<%
    if(userCategories == null || userCategories.size() < 1) {
        %>
        <h2>No Market Data Available</h2>
        <%
    } else {
        %>
        <h3>Top Buyers by Category</h3>
        <%
        String category = "";
        for(UserCategory userCategory : userCategories)
            if(!category.equals(userCategory.getCategory()))
            {
                category = userCategory.getCategory();
                %>
                <h3>Category: <%=category%></h3>
                <p>Name:<%=userCategory.getUser().getFirstName()%>    Email: <%= userCategory.getUser().getEmail()%></p>
                <p style="text-indent: 5em ">purchased <%=userCategory.getCount()%> book(s)</p><br>
                <%
            } else {
                %>
                <p>Name:<%=userCategory.getUser().getFirstName()%>    Email: <%= userCategory.getUser().getEmail()%></p>
                <p style="text-indent: 5em ">purchased <%=userCategory.getCount()%> book(s)</p><br>
                <%
            }
    }
%>
</section>
<jsp:include page="/includes/footer.jsp"/>
