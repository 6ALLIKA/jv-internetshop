package ma.internetshop.controller.user;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Order;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.service.OrderService;
import ma.internetshop.service.ShoppingCartService;
import ma.internetshop.service.UserService;

@WebServlet("/users/delete")
public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(id);
        shoppingCartService.delete(shoppingCart.getId());
        List<Order> orders = orderService.getUserOrders(userService.get(id));
        for (Order order : orders) {
            orderService.delete(order.getId());
        }
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
