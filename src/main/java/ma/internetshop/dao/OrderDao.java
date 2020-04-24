package ma.internetshop.dao;

import java.util.List;
import java.util.Optional;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;

public interface OrderDao {

    Order create(Order order);

    List<Product> get(Long id);

    Optional<Order> getOrder(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
