package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.Application;
import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.data.repository.ApplicationRepository;
import ch.heigvd.amt.wp1.data.repository.UserRepository;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;

import javax.ejb.EJB;
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

@WebServlet(urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    @EJB
    private ApplicationRepository applicationRepository;

    @EJB
    private UserRepository userRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameterMap().containsKey("action") ? request.getParameter("action").toUpperCase() : "";

        User user = (User) request.getSession().getAttribute("user");
        System.out.println("TESTOLI");
        if (action.equals("RESET")) {
            String user_id = request.getParameter("user_id");
            System.out.println("--- Reset the password of user with id : " + user_id + " ---");
        }

        List<User> users = userRepository.findAll();
        System.out.println(users);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
}
