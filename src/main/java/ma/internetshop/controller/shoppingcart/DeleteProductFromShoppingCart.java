package ma.internetshop.controller.shoppingcart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.service.ProductService;
import ma.internetshop.service.ShoppingCartService;

@WebServlet("/shoppingcart/delete")
public class DeleteProductFromShoppingCart extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("id");
        Long id = Long.valueOf(productId);

        Product product = productService.get(id);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        shoppingCartService.deleteProduct(shoppingCart, product);

        resp.sendRedirect(req.getContextPath() + "/shoppingcart");
    }
}
