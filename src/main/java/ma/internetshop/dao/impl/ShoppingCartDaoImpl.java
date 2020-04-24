package ma.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import ma.internetshop.dao.ShoppingCartDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCarts
                .stream()
                .filter(cart -> cart.getUser().getId().equals(userId))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return Storage.shoppingCarts
                .stream()
                .filter(cart -> cart.getId().equals(shoppingCart.getId()))
                .flatMap(cart -> cart.getProducts().stream())
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(x -> shoppingCart.getId().equals(Storage.shoppingCarts.get(x).getId()))
                .forEach(i -> Storage.shoppingCarts.set(i, shoppingCart));
        return shoppingCart;
    }
}
