
<%--
  Created by IntelliJ IDEA.
  User: Christopher
  Date: 2/24/2015
  Time: 18:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="post" name="MailUtil" id="MailUtil" action="/MailUtil">
    <label for="emailTo">To: </label>
    <input type="text" id="emailTo" name="emailTo" required/>
    <div id="feedback1" class="feedback"></div>

    <label for="emailSubject">Subject: </label>
    <input type="text" id="emailSubject" name="emailSubject"  required/>
    <div id="feedback2" class="feedback"></div>

    <textarea cols="80" rows="10" id="emailBody" name="emailBody"></textarea>
    <div id="feedback3" class="feedback"></div>

    <input type="submit" id="submit" value="Send"/>
</form>
</body>
</html>
