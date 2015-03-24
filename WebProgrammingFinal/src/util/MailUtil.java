package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Christopher on 2/24/2015.
 */
@WebServlet(name = "MailUtil", urlPatterns ={"/MailUtil"} )
public class MailUtil extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String username = "christran206@gmail.com";
        final String password = "xpmvhdmceewskjbq";

        String emailTo = request.getParameter("emailTo");
        String emailSubject = request.getParameter("emailSubject");
        String emailBody = request.getParameter("emailBody");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };

        try {
            Properties prop = new Properties();
            prop.put("mail.transport.protocol","smtps");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host","smtp.gmail.com");
            prop.put("mail.smtp.port",587);
            prop.put("mail.smtp.auth","true");
            prop.put("mail.smtp.quitwait","false");

            Session mailSession;
            mailSession = Session.getDefaultInstance(prop,auth);

            Message message = new MimeMessage(mailSession);
            message.setSubject(emailSubject);
            message.setText(emailBody);

            Address fromAddress = new InternetAddress("christran206@gmail.com");
            Address toAddress = new InternetAddress(emailTo);
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO,toAddress);

            Transport.send(message);

            response.sendRedirect("/mailTest.jsp");

        } catch (MessagingException e) {
            e.toString();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
