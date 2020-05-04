package ma.internetshop.controller.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.service.OrderService;
import ma.internetshop.service.ShoppingCartService;

@WebServlet("/orders/create")
public class CreateOrderFromShoppingCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private static Long orderId = 1L;
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        orderService.completeOrder(shoppingCart);
        req.setAttribute("message", "Your order " + orderId++ * 3 + " successfully created");
        req.getRequestDispatcher("/WEB-INF/views/orders/userorderinfo.jsp").include(req, resp);
    }
}
