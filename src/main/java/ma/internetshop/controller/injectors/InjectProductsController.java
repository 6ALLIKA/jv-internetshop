package ma.internetshop.controller.injectors;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Product;
import ma.internetshop.service.ProductService;

@WebServlet("/index/injectproducts")
public class InjectProductsController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("ma.internetshop");
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product product = new Product("Item", new BigDecimal(100));
        Product product1 = new Product("Item1", new BigDecimal(100));
        Product product2 = new Product("Item2", new BigDecimal(100));
        Product product3 = new Product("Item3", new BigDecimal(100));
        Product product4 = new Product("Item4", new BigDecimal(100));
        Product product5 = new Product("Item5", new BigDecimal(100));

        productService.create(product);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.create(product4);
        productService.create(product5);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
