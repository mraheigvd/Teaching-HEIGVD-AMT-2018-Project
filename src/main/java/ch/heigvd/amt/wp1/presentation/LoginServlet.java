package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.data.repository.UserRepository;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;

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

    private UserRepository userRepository = new UserRepository();


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
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            System.out.println("Password chedk: " + passwordAuthentication.authenticate(password.toCharArray(), user.getPassword()));
            if (user != null && passwordAuthentication.authenticate(password.toCharArray(), user.getPassword())) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
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
