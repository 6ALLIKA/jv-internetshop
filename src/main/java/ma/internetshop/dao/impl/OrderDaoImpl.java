package ma.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import ma.internetshop.dao.OrderDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public List<Product> get(Long id) {
        return Storage.orders.stream()
                .filter(order -> order.getId().equals(id))
                .map(Order::getProducts)
                .findFirst()
                .get();
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return Storage.orders
                .stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(order -> order.getId().equals(id));
    }
}
