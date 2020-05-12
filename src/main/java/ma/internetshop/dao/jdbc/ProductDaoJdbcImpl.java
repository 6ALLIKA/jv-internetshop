package ma.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.internetshop.dao.ProductDao;
import ma.internetshop.exceptions.DataProcessingException;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Product;
import ma.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    private static final int ID_COLUMN = 1;
    private static final Logger LOGGER = Logger.getLogger(ProductDaoJdbcImpl.class);

    @Override
    public Product create(Product element) {
        String query = "INSERT INTO products"
                + " (product_name, product_price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setBigDecimal(2, element.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long productId = resultSet.getLong(ID_COLUMN);
            element.setId(productId);
            LOGGER.info("Successful INSERT product in mySQL with ID " + productId);
            return element;
        } catch (SQLException ex) {
            LOGGER.error("Can't INSERT product in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't INSERT product in mySQL internet_shop", ex);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id=?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                Product product = getProductFromResultSet(resultSet);
                return Optional.of(product);
            }
        } catch (SQLException ex) {
            LOGGER.error("Can't FIND product by ID "
                    + id + " in mySQL", ex);
            throw new DataProcessingException("Can't FIND product by ID "
                    + id + " in mySQL internet_shop", ex);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException ex) {
            LOGGER.error("Can't GET products in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't GET products in mySQL internet_shop", ex);
        }
    }

    @Override
    public Product update(Product element) {
        String query = "UPDATE products "
                + "SET product_name = ?, product_price = ? "
                + "WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, element.getName());
            statement.setBigDecimal(2, element.getPrice());
            statement.setLong(3, element.getId());
            statement.executeUpdate();
            LOGGER.info("Product with ID=" + element.getId() + " was UPDATED");
            return element;
        } catch (SQLException ex) {
            LOGGER.error("Can't UPDATE product by ID "
                    + element.getId()
                    + "in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't UPDATE product by ID "
                    + element.getId()
                    + "in mySQL internet_shop", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE  FROM products "
                + "WHERE product_id=?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            LOGGER.info("Successful DELETE product in mySQL with ID " + id);
            return true;
        } catch (SQLException ex) {
            LOGGER.error("Can't DELETE product by ID " + id
                    + "in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't DELETE product by ID " + id
                    + "in mySQL internet_shop", ex);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("product_id");
        String name = resultSet.getString("product_name");
        BigDecimal price = resultSet.getBigDecimal("product_price");
        Product product = new Product(name, price);
        product.setId(id);
        return product;
    }
}
