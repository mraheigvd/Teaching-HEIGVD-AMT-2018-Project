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
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/reset")
public class ResetPasswordServlet extends HttpServlet {


    @EJB
    private UserRepository userRepository;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/reset_password.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password-confirmation");
        Map<String, String> errors = new HashMap<>();

        if (password.isEmpty())
            errors.put("password_error", "Password is empty");
        if (passwordConfirmation.isEmpty())
            errors.put("password_confirmation_error", "Password confirmation is empty");

        boolean loggedIn = request.getSession().getAttribute("user") != null;
        if (loggedIn && errors.isEmpty() && password.equals(passwordConfirmation)) {
            User user = (User) request.getSession().getAttribute("user");
            user.setPassword(passwordAuthentication.hash(password.toCharArray()));
            user.setPasswordIsExpired(false);
            userRepository.update(user);
        } else {
            errors.put("password_error", "Password are not the same");
        }

        // Check if errors
        if (errors.isEmpty()) {
            request.getSession().invalidate();
            request.setAttribute("passwordChanged", "Your password has been changed");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            for (Map.Entry<String, String> error : errors.entrySet()) {
                System.out.println(error.getKey());
                request.setAttribute(error.getKey(), error.getValue());
            }
            request.getRequestDispatcher("/WEB-INF/pages/reset_password.jsp").forward(request, response);
        }
    }
}
