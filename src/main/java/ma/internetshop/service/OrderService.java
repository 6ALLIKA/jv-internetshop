package ma.internetshop.service;

import java.util.List;
import ma.internetshop.model.Order;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;

public interface OrderService extends GenericService<Order, Long> {

    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(User user);

}
