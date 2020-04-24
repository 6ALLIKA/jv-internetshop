package ma.internetshop.service.impl;

import java.util.List;
import ma.internetshop.dao.ShoppingCartDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Inject;
import ma.internetshop.lib.Service;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        boolean isExist = shoppingCartDao.getAll().stream()
                .anyMatch(cart -> cart.getId().equals(shoppingCart.getId()));
        if (!isExist) {
            shoppingCartDao.create(shoppingCart);
        }
        shoppingCartDao.getByUserId(shoppingCart.getUser().getId()).get().getProducts().add(product);
        return shoppingCartDao.update(shoppingCartDao.getByUserId(shoppingCart.getUser().getId()).get());
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCart.getProducts().removeIf(prod -> prod.getId().equals(product.getId()))) {
            shoppingCartDao.update(shoppingCart);
            return true;
        }
        return false;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getByUserId(userId).get();
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return shoppingCartDao.getAllProducts(shoppingCart);
    }
}
