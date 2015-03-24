<%@ page import="bookstore.business.User" %>
<%--
  Created by IntelliJ IDEA.
  User: v-ctran
  Date: 3/12/2015
  Time: 7:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./includes/header.jsp"/>
<!--Left Navigation Bar -->
<%
    User user = (User)session.getAttribute("user");

%>

<!--Middle Section -->
<section class="user">
    <%
        if(user == null) {
        %>
        <h1>No user found</h1>
        <%
        } else {
    %>
    <form action="/user/orderHistory" method="post">
        <input type="submit" value="Order History" class="left_margin">
    </form>

    <form action="/user/updateUser" method="post">
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
        <em class="required">*</em><br><br>

        <p>Current password Required to change old password</p>
        <label>Current Password</label>
        <input type="text" name="currentPasswd" value="">
        <em class="required">**</em><br>

        <label>New Password</label>
        <input type="text" name="newPasswd" value="">
        <em class="required">**</em><br>

        <label>&nbsp;</label>
        <input type="submit" value="Continue">
    </form>
<%
    }
%>
</section>

<!-- Right column-->

<jsp:include page="./includes/footer.jsp"/>
