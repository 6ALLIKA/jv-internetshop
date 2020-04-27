package ma.internetshop.service;

import java.util.List;
import ma.internetshop.dao.GenericDao;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;

public interface ShoppingCartService extends GenericService<ShoppingCart, Long> {

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    ShoppingCart get(Long userId);

    List<Product> getAllProducts(ShoppingCart shoppingCart);

    void clear(ShoppingCart shoppingCart);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);
}
