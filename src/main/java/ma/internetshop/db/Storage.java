package ma.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    private static Long userID = 0L;
    private static Long productID = 0L;
    private static Long soppingCartID = 0L;
    private static Long orderID = 0L;

    public static void addProduct(Product product) {
        product.setId(++productID);
        products.add(product);
    }

    public static void addUser(User user) {
        user.setId(++userID);
        users.add(user);
    }

    public static void addOrder(Order order) {
        order.setId(++orderID);
        orders.add(order);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(++soppingCartID);
        shoppingCarts.add(shoppingCart);
    }
}
