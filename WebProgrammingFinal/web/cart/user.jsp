<%@ page import="bookstore.business.User" %>
<%@ page import="bookstore.data.UserDB" %>
<%@ page import="bookstore.util.CookieUtil" %>

<jsp:include page="/includes/header.jsp" />
<%-- Use the following left column instead after you configure a
     secure connection as described in chapter 15.
<jsp:include page="/includes/column_left_all_absolute.jsp" />
--%>

<!-- begin middle column -->
<%
    HttpSession httpSession = request.getSession();
    Cookie[] cookies = request.getCookies();
    String email = CookieUtil.getCookieValue(cookies,"emailCookie");
    User user = UserDB.selectUser(email);
    if(user == null) {
        user = new User();
    }

%>
<section class="user">

<h4>Enter your name and contact information</h4>

<form action="/order/processUser" method=post>
    <p id="required">Required <span class="required">*</span></p>
    
    <label>First Name</label>
    <input type="text" name="firstName"  maxlength=20 
               value="<%= user.getFirstName()%>" required>
    <em class="required">*</em><br>
    
    <label>Last Name</label>
    <input type="text" name="lastName" value="<%= user.getLastName()%>" required>
    <em class="required">*</em><br>
    
    <label>Email Address</label>
    <input type="email" name="email" value="<%= user.getEmail()%>" required>
    <em class="required">*</em><br>

    <label>Address1</label>
    <input type="text" name="address1" value="<%= user.getAddress1()%>" required>
    <em class="required">*</em><br>
    
    <label>Address2</label>
    <input type="text" name="address2" value="<%= user.getAddress2()%>">
    <p class="required">&nbsp;</p><br>
    
    <label>City</label>
    <input type="text" name="city" value="<%= user.getCity()%>" required>
    <em class="required">*</em><br>
    
    <label>State</label>
    <input type="text" name="state" value="<%= user.getState()%>" required>
    <em class="required">*</em><br>
    
    <label>Zip Code</label>
    <input type="text" name="zip" value="<%= user.getZip()%>" required>
    <em class="required">*</em><br>
    
    <label>Country</label>
    <input type="text" name="country" value="<%= user.getCountry()%>" required>
    <em class="required">*</em><br>
    
    <label>&nbsp;</label>
    <input type="submit" value="Continue">
</form>
    
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />
