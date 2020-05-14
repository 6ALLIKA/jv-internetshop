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
import ma.internetshop.dao.OrderDao;
import ma.internetshop.exceptions.DataProcessingException;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Order;
import ma.internetshop.model.Product;
import ma.internetshop.model.User;
import ma.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final int ID_COLUMN = 1;
    private static final Logger LOGGER = Logger.getLogger(OrderDaoJdbcImpl.class);

    @Override
    public Order create(Order element) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long orderId = resultSet.getLong(ID_COLUMN);
            element.setId(orderId);
            insertOrdersProducts(element);
            LOGGER.info("Successful INSERT order in mySQL with ID " + orderId);
            return element;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't INSERT order in mySQL internet_shop", ex);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCopyOfOrder(resultSet));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't FIND product by ID "
                    + id + " in mySQL internet_shop", ex);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders;";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                orders.add(getCopyOfOrder(resultSet));
            }
            return orders;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't SELECT orders in mySQL internet_shop", ex);
        }
    }

    @Override
    public Order update(Order element) {
        String query = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, element.getUser().getId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
            deleteOrderFromOrdersProducts(element.getId());
            insertOrdersProducts(element);
            LOGGER.info("Order with ID=" + element.getId() + " was UPDATED");
            return element;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't UPDATE order by ID "
                    + element.getId()
                    + "in mySQL internet_shop", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM orders WHERE order_id = ?;";
        return deleteById(id, query);
    }

    public boolean deleteProductFromOrder(Long id) {
        String query = "DELETE FROM orders_products WHERE product_id = ?;";
        return deleteById(id, query);
    }

    private boolean deleteById(Long id, String query) {
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            deleteOrderFromOrdersProducts(id);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int resultSet = statement.executeUpdate();
            return resultSet != 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't DELETE order in mySQL with ID " + id, ex);
        }
    }

    private Order getCopyOfOrder(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        return new Order(orderId, getProductsOfOrder(orderId), getUserOfOrder(userId));
    }

    private List<Product> getProductsOfOrder(Long orderId) {
        String query = "SELECT products.product_id, product_name, price  "
                + "FROM orders_products INNER JOIN products "
                + "ON orders_products.product_id = products.product_id "
                + "WHERE orders_products.order_id = ?;";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("product_name");
                BigDecimal price = resultSet.getBigDecimal("price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't FIND user from order by ID "
                    + orderId + " in mySQL internet_shop", ex);
        }
    }

    private User getUserOfOrder(Long userId) {
        String query = "SELECT user_name, login, pass "
                + "FROM orders INNER JOIN users "
                + "ON orders.user_id = users.user_id "
                + "WHERE orders.user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String userName = resultSet.getString("user_name");
            String userLogin = resultSet.getString("login");
            String userPass = resultSet.getString("pass");
            User user = new User(userName, userLogin, userPass);
            user.setId(userId);
            return user;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't FIND user from order by ID "
                    + userId + " in mySQL internet_shop", ex);
        }
    }

    private void deleteOrderFromOrdersProducts(Long orderId) throws SQLException {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        }
    }

    private void insertOrdersProducts(Order order) throws SQLException {
        String insertOrdersProductsQuery = "INSERT INTO orders_products (order_id, product_id) "
                + "VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            for (Product product : order.getProducts()) {
                PreparedStatement insertStatement =
                        connection.prepareStatement(insertOrdersProductsQuery);
                insertStatement.setLong(1, order.getId());
                insertStatement.setLong(2, product.getId());
                insertStatement.executeUpdate();
            }
        }
    }
}
