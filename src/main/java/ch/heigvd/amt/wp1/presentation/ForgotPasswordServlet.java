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

    final String SEARCHUSER = "SEARCHUSER";

    @EJB
    private ApplicationRepository applicationRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private EmailSender emailSender;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/forgot_password.jsp").forward(request, response);
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

                //send mail
            } else {
                messages.put("login", "Bad credentials, please try again");
            }
        }
    }
}
