package ma.internetshop.controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ma.internetshop.exceptions.AuthenticationException;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.User;
import ma.internetshop.security.AuthenticationService;
import ma.internetshop.web.filters.AuthorizationFilter;
import org.apache.log4j.Logger;

@WebServlet("/users/login")
public class LoginController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private AuthenticationService authenticationService
            = (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pass");

        try {
            User user = authenticationService.login(login, password);
            HttpSession session = req.getSession();
            session.setAttribute("user_id", user.getId());
        } catch (AuthenticationException e) {
            LOGGER.info("Unsuccessful login");
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/users/login.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
