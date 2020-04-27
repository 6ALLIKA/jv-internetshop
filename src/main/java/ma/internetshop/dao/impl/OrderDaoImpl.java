package ma.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import ma.internetshop.dao.OrderDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order element) {
        Storage.addOrder(element);
        return element;
    }

    @Override
    public Optional<Order> get(Long id) {
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
    public Order update(Order element) {
        IntStream.range(0, Storage.orders.size())
                .filter(x -> element.getId().equals(Storage.orders.get(x).getId()))
                .forEach(i -> Storage.orders.set(i, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(order -> order.getId().equals(id));
    }
}
