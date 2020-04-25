package ma.internetshop.service;

import java.util.List;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    ShoppingCart getByUserId(Long userId);

    List<Product> getAllProducts(ShoppingCart shoppingCart);

    void clear(ShoppingCart shoppingCart);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);
}
