package ma.internetshop.dao;

import java.util.Optional;
import ma.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long>{
    Optional<ShoppingCart> getByUserId(Long userId);
}
