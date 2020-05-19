package ma.internetshop.dao;

import ma.internetshop.model.Order;

public interface OrderDao extends GenericDao<Order, Long> {
    public boolean deleteProductFromOrder(Long id);
}
