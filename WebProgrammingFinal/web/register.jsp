<jsp:include page="./includes/header.jsp"/>
<!--Left Navigation Bar -->
<section>
<form action="/user/userRegister" method="post">
    <fieldset>
        <legend class="legend_text"> Your Login Info</legend>
        <div><span class="star">*</span>
            <label for="email">Email Address:</label>
            <input type="text" name="email" id="email" required="required"/></div>
        <div><span class="star">*</span>
            <label for="passwd">Password: </label>
            <input type="password" name="passwd" id="passwd"  required="required"/></div>
        <div><span class="star">*</span>
            <label for="repasswd">Confirm Password: </label>
            <input type="password" name="repasswd" id="repasswd" required="required"/></div>
        <div id="warning" class="feedback"></div>
        <div><input type="submit" value="Sign Up" id="submit"/></div>
    </fieldset>
    <div><span class="star">*</span><label>Mandatory Information</label></div>
</form>
<script>
    var elMsg = document.getElementById('warning');

    function loginCheck(){
        if(document.getElementById('passwd').value==""){
            elMsg.textContent = "Please enter your password";
            document.getElementById('passwd').focus();
            return false;
        }
        if(document.getElementById('repasswd').value==""){
            elMsg.textContent = "Please confirm your password";
            document.getElementById('repasswd').focus();
            return false;
        }
        if(document.getElementById('passwd').value != document.getElementById('repasswd').value){
            elMsg.textContent = "Passwords do not match.";
            document.getElementById('passwd').focus();
            return false;
        }
        if(document.getElementById('email').value==""){
            elMsg.textContent = "Please enter your email";
            document.getElementById('email').focus();
            return false;
        }
    }

    var elSubmit = document.getElementById('submit');
    elSubmit.onclick =loginCheck;
</script>
</section>
<jsp:include page="./includes/footer.jsp"/>
