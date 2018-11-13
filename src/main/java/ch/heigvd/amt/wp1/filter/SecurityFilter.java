package ch.heigvd.amt.wp1.filter;

import ch.heigvd.amt.wp1.data.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("UTF-8");
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        // Don't filter resources and registration page
        if (request.getRequestURI().matches(".*(css|jpg|png|gif|js|register|reset)$")) {
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println("SecurityFilter => Resources or basic pages asked");
            return;
        }

        if (request.getAttribute("user") == null) {
            String loginURI = request.getContextPath() + "/login";

            boolean loggedIn = session != null && session.getAttribute("user") != null;
            boolean loginRequest = request.getRequestURI().equals(loginURI);

            System.out.println("Requested URI: " + request.getRequestURI());

            if (loggedIn) {
                System.out.println("SecurityFilter => User already logged");

                User user = (User) session.getAttribute("user");

                // Protect ADMIN area
                if (request.getRequestURI().contains(request.getContextPath() + "/admin/")) {
                    System.out.println("Contains : " + request.getContextPath() + "/admin/");
                    if (user != null && user.getIsAdmin()) {
                        filterChain.doFilter(request, servletResponse);
                    } else {
                        httpResponse.sendRedirect(request.getContextPath() + "/profile");
                    }
                } else {
                    if (request.getRequestURI().endsWith("/"))
                        httpResponse.sendRedirect(request.getContextPath() + "/profile");

                    System.out.println(user);
                    if (user.getPasswordIsExpired()) {
                        httpResponse.sendRedirect(request.getContextPath() + "/reset");
                        return;
                    }

                    filterChain.doFilter(request, servletResponse);
                }
            } else if (loginRequest) {
                System.out.println("SecurityFilter => Login request");
                filterChain.doFilter(request, servletResponse);
            } else {
                System.out.println("SecurityFilter => To login");
                httpResponse.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }


    @Override
    public void destroy() {

    }
}
