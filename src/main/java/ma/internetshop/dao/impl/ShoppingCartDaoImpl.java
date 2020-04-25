package ma.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import ma.internetshop.dao.ShoppingCartDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart element) {
        Storage.addShoppingCart(element);
        return element;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCarts
                .stream()
                .filter(cart -> cart.getId().equals(id))
                .findFirst();
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
    public ShoppingCart update(ShoppingCart element) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(x -> element.getId().equals(Storage.shoppingCarts.get(x).getId()))
                .forEach(i -> Storage.shoppingCarts.set(i, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(cart -> cart.getId().equals(id));
    }
}
