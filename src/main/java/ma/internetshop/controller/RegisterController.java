package ma.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;
import ma.internetshop.service.ShoppingCartService;
import ma.internetshop.service.UserService;

public class RegisterController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ma.internetshop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        String repPassword = req.getParameter("rep-pass");

        if (password.equals(repPassword)) {
            User user = new User(name, login, password);
            userService.create(user);
            ShoppingCart shoppingCart = new ShoppingCart(user);
            shoppingCartService.create(shoppingCart);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Your repeat password is wrong");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
