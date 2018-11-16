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

@WebServlet(urlPatterns = "/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    final String DISABLE = "DISABLE";
    final String RESET = "RESET";

    @EJB
    private ApplicationRepository applicationRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private EmailSender emailSender;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*//Retrieve action
        String action = request.getParameterMap().containsKey("action") ? request.getParameter("action").toUpperCase() : "";

        //Test if disable has been required
        if (action.equals(DISABLE)) {
            Long userId = Long.parseLong(request.getParameter("user_id"));

            if (userId != null) {
                //find the user by his/her id and disable him/her
                userRepository.disable(userRepository.findById(userId));

            }
        } else if (action.equals(RESET)) {
            Long userId = Long.parseLong(request.getParameter("user_id"));
            User userTargetted = userRepository.findById(userId);

            final String newPassword = PasswordAuthentication.generateAlphanumString(8);
            userTargetted.setPassword(passwordAuthentication.hash(newPassword.toCharArray()));
            userTargetted.setPasswordIsExpired(true);
            userRepository.update(userTargetted);

            final String body = "Dear " + userTargetted.getFirstname() + " " + userTargetted.getLastname() + ",\r\n\r\n" +
                    "An admin requested a password reset for you. Please use this password for your next connection : " + newPassword + "\r\n\r\n" +
                    "a new password will be ask on your next login. \r\n\r\n Your Gamification team.";
            try {
                emailSender.sendEmail(userTargetted.getEmail(), "Gamification password reset", body);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

        LinkedList<User> users = (LinkedList<User>) userRepository.findAll();

        request.setAttribute("users", users); */
        request.getRequestDispatcher("/WEB-INF/pages/forgot_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
