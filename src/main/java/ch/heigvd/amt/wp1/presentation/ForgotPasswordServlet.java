package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.User;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/forgotPassword")
public class ForgotPasswordServlet extends HttpServlet {

    @EJB
    private UserRepository userRepository;

    @EJB
    private EmailSender emailSender;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        if (!token.isEmpty() && !email.isEmpty()) {
            User user = userRepository.findByEmail(email);
            if (user != null && user.getTokenValidate().equals(token)) {
                request.setAttribute("email", email);
                request.setAttribute("token", token);
                request.getRequestDispatcher("/WEB-INF/pages/forgot_password.jsp").forward(request, response);
            }
        }
        response.sendRedirect(request.getContextPath() + "/");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password-confirmation");


        if (!token.isEmpty() && !email.isEmpty()) {
            Map<String, String> errors = new HashMap<>();

            if (password.isEmpty())
                errors.put("password_error", "Password is empty");
            if (passwordConfirmation.isEmpty())
                errors.put("password_confirmation_error", "Password confirmation is empty");

            if (password.equals(passwordConfirmation)) {
                User user = userRepository.findByEmail(email);
                user.setPassword(passwordAuthentication.hash(password.toCharArray()));
                user.setTokenValidate(null);
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
}
