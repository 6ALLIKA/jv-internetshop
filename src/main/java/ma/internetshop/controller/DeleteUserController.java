package ma.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.service.UserService;

@WebServlet("/users/delete")
public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}