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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    final String DISABLE = "DISABLE";

    @EJB
    private ApplicationRepository applicationRepository;

    @EJB
    private UserRepository userRepository;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Retrieve action
        String action = request.getParameterMap().containsKey("action") ? request.getParameter("action").toUpperCase() : "";
        //Retrieve all users
        LinkedList<User> users = (LinkedList<User>) userRepository.findAll();

        //Test if disable has been required
        if (action.equals(DISABLE)) {
            Long userId = Long.parseLong(request.getParameter("user_id"));

            if (userId != null) {
                //find the user by his/her id and disable him/her
                userRepository.disable(userRepository.findById(userId));
            }
        }

        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
