package ma.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ma.internetshop.lib.Injector;
import ma.internetshop.service.ProductService;

public class DeleteProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("ma.internetshop");
    private ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        productService.delete(Long.parseLong(id));
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }
}
