package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.Application;
import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.data.repository.ApplicationRepository;
import ch.heigvd.amt.wp1.data.repository.UserRepository;
import ch.heigvd.amt.wp1.util.EmailSender;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/requestForgotPassword")
public class RequestForgotPasswordServlet extends HttpServlet {

    @EJB
    private UserRepository userRepository;

    @EJB
    private EmailSender emailSender;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/request_forgot_password.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        Map<String, String> messages = new HashMap<>();

        if (email == null || email.isEmpty()) {
            messages.put("email", "Please enter email");
        }

        if (messages.isEmpty()) {
            // Find and try to authenticate the user
            User user = userRepository.findByEmail(email);

            if (user != null) {
                //generate key
                final String token = PasswordAuthentication.generateAlphanumString(40);

                String URI =  EmailSender.getBaseUrl(request) + "/forgotPassword?email=" + user.getEmail() + "&token=" + token;
                final String body = "Dear " + user.getFirstname() + " " + user.getLastname() + ",\r\n\r\n" +
                        "Please use this <a href='" + URI + "'>LINK</a>." + "\r\n\r\n" +
                        "you need to set a new password. \r\n\r\n Your Gamification team.";

                //send mail
                try {
                    user.setTokenValidate(token);
                    userRepository.update(user);
                    emailSender.sendEmail(user.getEmail(), "Gamification reset your password", body);
                    System.out.println("PASSWORD RESET MAIL SENT");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("END OF DOPOST");

            messages.put("message", "If your email is on our database, a mail to change your password will be sent.");

            for (Map.Entry<String, String> error : messages.entrySet()) {
                request.setAttribute(error.getKey(), error.getValue());
            }
            request.getRequestDispatcher("/WEB-INF/pages/request_forgot_password.jsp").forward(request, response);
        }
    }
}
