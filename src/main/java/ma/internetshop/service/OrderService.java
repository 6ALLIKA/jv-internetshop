package ma.internetshop.service;

import java.util.List;
import ma.internetshop.model.Order;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;

public interface OrderService {

    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(User user);

    Order getOrder(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
