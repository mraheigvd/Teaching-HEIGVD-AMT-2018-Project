package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.data.repository.UserRepository;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserRepository userRepository;

    private PasswordAuthentication passwordAuthentication = new PasswordAuthentication();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Map<String, String> messages = new HashMap<>();

        if (email == null || email.isEmpty()) {
            messages.put("email", "Please enter email");
        }

        if (password == null || password.isEmpty()) {
            messages.put("password", "Please enter password");
        }

        if (messages.isEmpty()) {
            // Find and try to authenticate the user
            User user = userRepository.findByEmail(email);
            if (user != null && passwordAuthentication.authenticate(password.toCharArray(), user.getPassword())) {
                if (user.getIsEnable()) {
                    System.out.println("Password chedk: " + passwordAuthentication.authenticate(password.toCharArray(), user.getPassword()));
                    request.getSession().setAttribute("user", user);

                    if(user.getPasswordIsExpired()){
                        response.sendRedirect(request.getContextPath() + "/reset");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/profile");
                    }

                    return;
                } else {
                    request.setAttribute("account_disabled", "Your account has been blocked. Please contact the administrator.");
                }
            } else {
                messages.put("login", "Bad credentials, please try again");
            }
        }

        for (Map.Entry<String, String> error : messages.entrySet()) {
            System.out.println(error.getKey());
            request.setAttribute(error.getKey(), error.getValue());
        }
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}
