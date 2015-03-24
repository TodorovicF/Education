<%
    Cookie killMyCookie = new Cookie("emailCookie", null);
    killMyCookie.setMaxAge(0);
    killMyCookie.setPath("/");
    response.addCookie(killMyCookie);
    session.setAttribute("cart", null);
    session.setAttribute("user",null);
    response.sendRedirect("/index.jsp");
%>
