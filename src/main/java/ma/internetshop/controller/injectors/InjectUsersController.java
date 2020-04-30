package ma.internetshop.controller.injectors;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;
import ma.internetshop.service.ShoppingCartService;
import ma.internetshop.service.UserService;

@WebServlet("/index/injectusers")
public class InjectUsersController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private UserService userService
            = (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User("user", "login", "pass");
        User user1 = new User("user1", "login", "pass");
        User user2 = new User("user2", "login", "pass");
        User user3 = new User("user3", "login", "pass");
        User user4 = new User("user4", "login", "pass");
        User user5 = new User("user5", "login", "pass");

        userService.create(user);
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.create(user4);
        userService.create(user5);

        shoppingCartService.create(new ShoppingCart(user));

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
