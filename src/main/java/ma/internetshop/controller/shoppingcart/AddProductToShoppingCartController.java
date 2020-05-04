package ma.internetshop.controller.shoppingcart;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.service.ProductService;
import ma.internetshop.service.ShoppingCartService;

@WebServlet("/products/addtoshoppingcart")
public class AddProductToShoppingCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        Product product = productService.get(Long.parseLong(req.getParameter("id")));
        shoppingCartService.addProduct(shoppingCart, product);
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }
}
