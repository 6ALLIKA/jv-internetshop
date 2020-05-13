package ma.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ma.internetshop.dao.ShoppingCartDao;
import ma.internetshop.exceptions.DataProcessingException;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Product;
import ma.internetshop.model.ShoppingCart;
import ma.internetshop.model.User;
import ma.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    private static final int ID_COLUMN = 1;
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartDaoJdbcImpl.class);

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(getProductsFromShoppingCartId(shoppingCart.getId()));
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException ex) {
            LOGGER.error("Can't FIND cart in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't FIND cart in mySQL internet_shop", ex);
        }
    }

    @Override
    public ShoppingCart create(ShoppingCart element) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long cartId = resultSet.getLong(ID_COLUMN);
            element.setId(cartId);
            insertCartsProducts(element);
            LOGGER.info("Successful INSERT cart in mySQL with ID " + cartId);
            return element;
        } catch (SQLException ex) {
            LOGGER.error("Can't INSERT cart in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't INSERT cart in mySQL internet_shop", ex);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(getProductsFromShoppingCartId(id));
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException ex) {
            LOGGER.error("Can't FIND cart in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't FIND cart in mySQL internet_shop", ex);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts;";
        List<ShoppingCart> allShoppingCarts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(getProductsFromShoppingCartId(shoppingCart.getId()));
                allShoppingCarts.add(shoppingCart);
            }
            return allShoppingCarts;
        } catch (SQLException ex) {
            LOGGER.error("Can't SELECT carts in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't SELECT carts in mySQL internet_shop", ex);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        String query = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, element.getUser().getId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
            deleteShoppingCartFromCartsProducts(element.getId());
            insertCartsProducts(element);
            LOGGER.info("Shopping cart with ID=" + element.getId() + " was UPDATED");
            return element;
        } catch (SQLException ex) {
            LOGGER.error("Can't UPDATE cart in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't UPDATE cart in mySQL internet_shop", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            deleteShoppingCartFromCartsProducts(id);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException ex) {
            LOGGER.error("Can't DELETE cart in mySQL internet_shop", ex);
            throw new DataProcessingException("Can't DELETE cart in mySQL internet_shop", ex);
        }
    }

    private void insertCartsProducts(ShoppingCart shoppingCart) throws SQLException {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id) "
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            for (Product product : shoppingCart.getProducts()) {
                PreparedStatement insertStatement =
                        connection.prepareStatement(query);
                insertStatement.setLong(1, shoppingCart.getId());
                insertStatement.setLong(2, product.getId());
                insertStatement.executeUpdate();
            }
        }
    }

    private void deleteShoppingCartFromCartsProducts(Long shoppingCartId) throws SQLException {
        String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCartId);
            statement.executeUpdate();
        }
    }

    private List<Product> getProductsFromShoppingCartId(Long shoppingCartId) throws SQLException {
        String query = "SELECT products.* FROM shopping_carts_products "
                + "JOIN products ON products.product_id = shopping_carts_products.product_id "
                + "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCartId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("product_id");
                String name = resultSet.getString("product_name");
                BigDecimal price = resultSet.getBigDecimal("product_price");
                Product product = new Product(name, price);
                product.setId(id);
                products.add(product);
            }
            return products;
        }
    }

    private User getUserOfShoppingCart(Long userId) {
        String query = "SELECT user_name, user_login, user_pass "
                + "FROM shopping_carts INNER JOIN users "
                + "ON shopping_carts.user_id = users.user_id "
                + "WHERE shopping_carts.user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String userName = resultSet.getString("user_name");
            String userLogin = resultSet.getString("user_login");
            String userPass = resultSet.getString("user_pass");
            User user = new User(userName, userLogin, userPass);
            user.setId(userId);
            return user;
        } catch (SQLException ex) {
            LOGGER.error("Can't FIND user from shopping cart by ID "
                    + userId + " in mySQL", ex);
            throw new DataProcessingException("Can't FIND user from shopping cart by ID "
                    + userId + " in mySQL internet_shop", ex);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(getUserOfShoppingCart(userId));
        shoppingCart.setId(id);
        return shoppingCart;
    }
}
