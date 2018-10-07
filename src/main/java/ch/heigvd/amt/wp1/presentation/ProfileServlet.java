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

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    private UserRepository userRepository = new UserRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Profile page requested");
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");

        Map<String, String> messages = new HashMap<>();

        if (firstname == null || firstname.isEmpty()) {
            messages.put("firstname_error", "Please enter a valid firstname");
        }

        if (lastname == null || lastname.isEmpty()) {
            messages.put("lastname_error", "Please enter a valid lastname");
        }

        if (messages.isEmpty()) {
            // Find and update user
            User user = userRepository.findByEmail(email);
            if (user != null) {
                // Save user details
                user.setFirstname(firstname);
                user.setLastname(lastname);
                userRepository.update(user);

                // Update user in session
                request.getSession().removeAttribute("user");
                request.getSession().setAttribute("user", user);
                request.setAttribute("success", "Correctly updated");
            } else {
                messages.put("error", "An error occured during the update. Please try again!");
            }
        }

        for (Map.Entry<String, String> error : messages.entrySet()) {
            System.out.println(error.getKey());
            request.setAttribute(error.getKey(), error.getValue());
        }
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

}
