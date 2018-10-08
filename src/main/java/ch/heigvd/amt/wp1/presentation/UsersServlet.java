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



}
