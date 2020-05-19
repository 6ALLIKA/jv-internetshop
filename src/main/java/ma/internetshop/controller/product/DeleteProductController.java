package ma.internetshop.controller.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.service.OrderService;
import ma.internetshop.service.ProductService;

@WebServlet("/products/delete")
public class DeleteProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        long productId = Long.parseLong(id);
        orderService.deleteProduct(productId);
        productService.delete(productId);
        resp.sendRedirect(req.getContextPath() + "/products/add");
    }
}
