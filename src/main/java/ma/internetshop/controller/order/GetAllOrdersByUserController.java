package ma.internetshop.controller.order;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Order;
import ma.internetshop.model.User;
import ma.internetshop.service.OrderService;
import ma.internetshop.service.UserService;

@WebServlet("/orders/byUser")
public class GetAllOrdersByUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        User user = userService.get(id);
        List<Order> allOrders = orderService.getUserOrders(user);
        req.setAttribute("orders", allOrders);
        req.setAttribute("id", id);
        req.getRequestDispatcher("/WEB-INF/views/orders/byUser.jsp").forward(req, resp);
    }
}
