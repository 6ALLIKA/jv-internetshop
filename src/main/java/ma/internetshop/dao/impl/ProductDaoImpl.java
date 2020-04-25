package ma.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import ma.internetshop.dao.ProductDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Product;

@Dao
public class ProductDaoImpl implements ProductDao {
    private static long count = 0;

    @Override
    public Product create(Product element) {
        Storage.addProduct(element);
        return element;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product element) {
        IntStream.range(0, Storage.products.size())
                .filter(x -> element.getId().equals(Storage.products.get(x).getId()))
                .forEach(i -> Storage.products.set(i, element));
        return element;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(product -> product.getId().equals(id));
    }
}
