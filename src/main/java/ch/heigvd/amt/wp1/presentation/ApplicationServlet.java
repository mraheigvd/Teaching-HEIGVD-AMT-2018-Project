package ch.heigvd.amt.wp1.presentation;

import ch.heigvd.amt.wp1.data.model.Application;
import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.data.repository.ApplicationRepository;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;

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

@WebServlet(urlPatterns = "/applications")
public class ApplicationServlet extends HttpServlet {

    private ApplicationRepository applicationRepository = new ApplicationRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameterMap().containsKey("action") ? request.getParameter("action").toUpperCase() : "";

        User user = (User) request.getSession().getAttribute("user");

        if (action.equals("DELETE")) {
            System.out.println("DELETE ACTION");
            Long appId = Long.parseLong(request.getParameter("app_id"));
            applicationRepository.delete(applicationRepository.findById(appId), user);
        } else if (action.equals("REGENERATE")) {

            // Security issue needs to check that the application is owned by current user
            System.out.println("REGENERATE ACTION");
            Long appId = Long.parseLong(request.getParameter("app_id"));
            Application app = applicationRepository.findById(appId);
            Application application = new Application(app.getId(), app.getName(),
                    app.getDescription(),
                    PasswordAuthentication.generateAlphanumString(30),
                    PasswordAuthentication.generateAlphanumString(64));
            applicationRepository.update(application);
        }

        List<Application> applications = applicationRepository.findByUser(user);
        System.out.println(applications);
        request.setAttribute("applications", applications);
        request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameterMap().containsKey("action") ? request.getParameter("action").toUpperCase() : "";
        User user = (User) request.getSession().getAttribute("user");
        Map<String, String> messages = new HashMap<>();

        if (action.equals("NEW")) {
            System.out.println("NEW ACTION");
            createApplication(user, request, messages);
        } else if (action.equals("EDIT")) {
            System.out.println("EDIT ACTION");
        } else {
            System.out.println("NOTHING");

        }

        for (Map.Entry<String, String> error : messages.entrySet()) {
            request.setAttribute(error.getKey(), error.getValue());
        }

        List<Application> applications = applicationRepository.findByUser(user);
        request.setAttribute("applications", applications);
        request.getRequestDispatcher("/WEB-INF/pages/applications.jsp").forward(request, response);
    }

    private Application createApplication(User user, HttpServletRequest request, Map<String, String> messages) {
        System.out.println("NEW action");
        String name = request.getParameter("name");
        String description = request.getParameter("description");


        if (name == null || name.isEmpty()) {
            messages.put("name_error", "Please enter a valid name");
        }

        if (description == null || description.isEmpty()) {
            messages.put("description_error", "Please enter a valid description");
        }

        if (messages.isEmpty()) {
            SecureRandom random = new SecureRandom();
            Application application = new Application(name,
                    description,
                    PasswordAuthentication.generateAlphanumString(30),
                    PasswordAuthentication.generateAlphanumString(64));
            // Find and try to authenticate the user
            application = applicationRepository.create(application, user);
            if (application.getId() == null ) {
                messages.put("error", "An error occured during the creation. Please retry.");
            }
            return application;
        }
        return null;
    }

}
