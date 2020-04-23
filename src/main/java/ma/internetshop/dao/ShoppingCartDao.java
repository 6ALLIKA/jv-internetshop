package ma.internetshop.dao;

import java.util.List;
import java.util.Optional;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {

    Optional<ShoppingCart> getByUserId(Long userId);

    List<Product> getAllProducts(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    ShoppingCart create(ShoppingCart shoppingCart);
}
