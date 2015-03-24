<jsp:include page="/includes/header.jsp" />

<section>
    <h1>Login Form - Error</h1>
    <p>You did not log in successfully.</p>
    <p>Please check your username and password and try again.</p>

    <form action="/user/userLogin" method="post">
        <label>Email Address</label>
        <input type="text" name="email"><br>
        <label>Password</label>
        <input type="password" name="password"><br>
        <label>&nbsp;</label>
        <input type="submit" value="Login">
    </form>
</section>

<jsp:include page="/includes/footer.jsp" />
