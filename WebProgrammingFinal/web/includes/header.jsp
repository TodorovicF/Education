<%--
  Created by IntelliJ IDEA.
  User: Christopher
  Date: 2/19/2015
  Time: 18:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.CookieUtil" %>
<%@ page import="bookstore.business.User" %>
<%
    String email = "";
    User user = (User) session.getAttribute("user");
    if(user != null) {
        Cookie[] cookies = request.getCookies();
        email = CookieUtil.getCookieValue(cookies, "emailCookie");
    }
%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="web/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="web/css/main.css"/>
    <title>BookStorePlaceholder</title>
    <link rel="stylesheet" href="/css/main.css"/>
</head>
<body>
<header>
    <img src="/images/storeLogo.jpg"/>
    <h1>
        BookStorePlaceholder
    </h1>
</header>
<nav id="nav_bar">
    <ul>
        <%
            if(email.equals("")) {
        %>
        <li>
            <div class="nav_horizontal">

                <form action="/user/userLogin" method = "post" >
                    <div class="horizontal_login">
                        <label>Email</label>
                        <input type="text" name="email">
                    </div>
                    <div class="horizontal_login">
                        <label>Password</label>
                        <input type="password" name="password">
                    </div>
                    <div class="horizontal_login">
                        <label>&nbsp;</label>
                        <input type="submit" value="Login">
                    </div>
                </form >
            </div>
        </li>
        <li>
            <a href="/register.jsp">Register</a>
        </li>
        <%
            } else {
        %>
        <li>
            <a href="/userProfile.jsp">User Profile</a>
        </li>
        <li>
            <a href="/logout.jsp">LogOut</a>
        </li>
        <%
            }
        %>
        <li>
            <a href="/admin/">Admin</a>
        </li>
        <li>
            <a href="/order/showCart">Show Cart</a>
        </li>
    </ul>
    <form action="/user/searchResults" method="get" style="float:right;">
        <input type="text" name="search" placeholder="Search">
        <select name="context">
            <option value="title">Title</option>
            <option value="isbn13">ISBN13</option>
            <option value="isbn10">ISBN10</option>
            <option value="author">Author</option>
            <option value="category">Category</option>
        </select>
        <input type="submit" value="Go">
    </form>
</nav>
<jsp:include page="/includes/home_left_col.jsp"/>
