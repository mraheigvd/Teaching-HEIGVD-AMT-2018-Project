package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.data.repository.UserRepository;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private UserRepository userRepository = new UserRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password-confirmation");

        Map<String, String> errors = new HashMap<>();

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);

        if (firstname.isEmpty())
            errors.put("firstname_error", "Firstname is empty");
        if (lastname.isEmpty())
            errors.put("lastname_error", "Lastname is empty");
        if (email.isEmpty())
            errors.put("email_error", "Email is empty");
        if (password.isEmpty())
            errors.put("password_error", "Password is empty");
        if (passwordConfirmation.isEmpty())
            errors.put("password_confirmation_error", "Password confirmation is empty");

        if ( ! password.equals(passwordConfirmation))
            errors.put("password_error", "Password and password confirmation are not the same");

        boolean isEmailAlreadyTaken = userRepository.findByEmail(email).getId() != null;

        // No errors, we can create the user
        if (errors.isEmpty() && ! isEmailAlreadyTaken) {
            // Hash and save the user
            PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
            String hashed_pass = passwordAuthentication.hash(password.toCharArray());
            System.out.println("Hashed pass: " + hashed_pass);
            User user = new User(email, firstname, lastname, hashed_pass, false, false, "");
            user.setIsEnable(true);
            user = userRepository.create(user);

            // Save user in session
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            // Redirect the user to his profile
            System.out.println(user);
            response.sendRedirect(request.getContextPath() + "/profile");
        }

        for (Map.Entry<String, String> error : errors.entrySet()) {
            System.out.println(error.getKey());
            request.setAttribute(error.getKey(), error.getValue());
        }

        if (isEmailAlreadyTaken)
            request.setAttribute("email_error", "Email already taken !");

        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

}
