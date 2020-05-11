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
        Product product = new Product("TINTIN A", new BigDecimal(10000));
        Product product1 = new Product("TINTIN B", new BigDecimal(15000));
        Product product2 = new Product("Starlink", new BigDecimal(30000));
        Product product3 = new Product("Starlink-1", new BigDecimal(30000));
        Product product4 = new Product("Starlink-2", new BigDecimal(30000));
        Product product5 = new Product("Starlink-3", new BigDecimal(30000));

        productService.create(product);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.create(product4);
        productService.create(product5);

        Product updateTest = new Product("update", new BigDecimal(30));
        updateTest.setId(6L);
        productService.update(updateTest);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
