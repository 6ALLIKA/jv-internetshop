package ma.internetshop.service;

import java.util.List;
import ma.internetshop.model.Product;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
