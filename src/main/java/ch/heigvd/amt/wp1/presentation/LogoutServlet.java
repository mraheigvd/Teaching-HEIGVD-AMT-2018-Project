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

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    private UserRepository userRepository = new UserRepository();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        System.out.println("Correctly logged out");
        response.sendRedirect("/");
    }

}
