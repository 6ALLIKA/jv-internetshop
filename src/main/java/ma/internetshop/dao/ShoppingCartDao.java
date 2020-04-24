package ma.internetshop.dao;

import java.util.List;
import java.util.Optional;
import ma.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {

    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

    List<ShoppingCart> getAll();

    ShoppingCart update(ShoppingCart shoppingCart);
}
