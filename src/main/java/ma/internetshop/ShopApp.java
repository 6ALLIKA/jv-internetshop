package ma.internetshop;

import java.math.BigDecimal;
import java.util.List;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Product;
import ma.internetshop.service.ProductService;

public class ShopApp {
    private static Injector injector = Injector.getInstance("ma.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        initializeDb(productService);

        List<Product> products = productService.getAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    public static void initializeDb(ProductService productService) {
        Product product = new Product("Shapka", new BigDecimal(300));
        Product product1 = new Product("Kyrtka", new BigDecimal(1000));
        Product product2 = new Product("Noski", new BigDecimal(5));
        Product product3 = new Product("Shtanu", new BigDecimal(40));

        productService.create(product);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        product3.setPrice(new BigDecimal(400));

        productService.update(product3);
        productService.delete(3L);
    }
}
