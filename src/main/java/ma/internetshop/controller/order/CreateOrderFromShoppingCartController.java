package ma.internetshop.controller.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.service.OrderService;
import ma.internetshop.service.ShoppingCartService;

@WebServlet("/shoppingcart/createorder")
public class CreateOrderFromShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        orderService.completeOrder(shoppingCart);
        req.setAttribute("message", "Your order successfully created");
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").include(req, resp);
    }
}
