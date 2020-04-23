package ma.internetshop.dao;

import java.util.List;
import java.util.Optional;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;
import ma.internetshop.model.User;

public interface OrderDao {

    Order create(List<Product> products, User user);

    List<Order> getUserOrders(User user);

    Optional<Order> getOrder(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
