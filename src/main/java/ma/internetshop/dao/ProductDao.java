package ma.internetshop.dao;

import java.util.List;
import java.util.Optional;
import ma.internetshop.model.Product;

public interface ProductDao {
    Product create(Product item);

    Optional<Product> get(Long id);

    List<Product> getAll();

    Product update(Product item);

    boolean delete(Long id);
}
