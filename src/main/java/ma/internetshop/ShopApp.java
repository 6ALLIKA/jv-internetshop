package ma.internetshop;

import java.math.BigDecimal;
import java.util.List;
import ma.internetshop.lib.Injector;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;
import ma.internetshop.service.OrderService;
import ma.internetshop.service.ProductService;
import ma.internetshop.service.ShoppingCartService;
import ma.internetshop.service.UserService;

public class ShopApp {
    private static final Injector injector = Injector.getInstance("ma.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

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

        List<Product> products = productService.getAll();
        for (Product p : products) {
            System.out.println(p.toString());
        }

        User user = new User("Sanya", "vztot", "parol");
        User user1 = new User("Polya", "login", "login");
        User user2 = new User("Admin", "Admin", "Admin");
        User user3 = new User("111", "111", "111");

        UserService userService = (UserService) injector.getInstance(UserService.class);

        userService.create(user);
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
        userService.delete(4L);

        List<User> users = userService.getAll();
        for (User p : users) {
            System.out.println(p.toString());
        }

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart cart = new ShoppingCart(user);
        ShoppingCart cart1 = new ShoppingCart(user1);

        shoppingCartService.addProduct(cart1, product);
        System.out.println(shoppingCartService.deleteProduct(cart1, product));

        shoppingCartService.addProduct(cart, product);
        List<Product> products1 =
                shoppingCartService.getAllProducts(shoppingCartService.getByUserId(1L));
        for (Product p : products1) {
            System.out.println(p.toString());
        }

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        Order order = orderService.completeOrder(cart);
        System.out.println(order);
    }
}
