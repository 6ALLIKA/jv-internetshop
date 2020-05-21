package ma.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import ma.internetshop.dao.UserDao;
import ma.internetshop.exceptions.DataProcessingException;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.Role;
import ma.internetshop.model.User;
import ma.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final int ID_COLUMN = 1;
    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    @Override
    public Optional<User> getByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't FIND user in mySQL internet_shop", ex);
        }
    }

    @Override
    public User create(User element) {
        String query = "INSERT INTO users (user_name, login, pass, salt) "
                + "VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setBytes(4, element.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long id = resultSet.getLong(ID_COLUMN);
            element.setId(id);
            insertUsersRoles(element);
            LOGGER.info("Successful INSERT user in mySQL with ID " + id);
            return element;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't INSERT user in mySQL internet_shop", ex);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(id));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't FIND user in mySQL internet_shop", ex);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users;";
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId()));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't SELECT users in mySQL internet_shop", ex);
        }
    }

    @Override
    public User update(User element) {
        String query = "UPDATE users SET user_name = ?, login = ?, pass = ?, salt = ? "
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setBytes(4, element.getSalt());
            statement.setLong(5, element.getId());
            statement.executeUpdate();
            deleteUserFromUsersRoles(element.getId());
            insertUsersRoles(element);
            LOGGER.info("Shopping user with ID=" + element.getId() + " was UPDATED");
            return element;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't UPDATE user in mySQL internet_shop", ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            deleteUserFromUsersRoles(id);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            LOGGER.info("User with id " + id + " was deleted.");
            return numberOfRowsDeleted != 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't DELETE user in mySQL internet_shop", ex);
        }
    }

    private void insertUsersRoles(User user) throws SQLException {
        String selectRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String insertUsersRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            for (Role role : user.getRoles()) {
                PreparedStatement selectStatement =
                        connection.prepareStatement(selectRoleIdQuery);
                selectStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = selectStatement.executeQuery();
                resultSet.next();
                PreparedStatement insertStatement =
                        connection.prepareStatement(insertUsersRolesQuery);
                insertStatement.setLong(1, user.getId());
                insertStatement.setLong(2, resultSet.getLong("role_id"));
                insertStatement.executeUpdate();
            }
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("user_name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("pass");
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(name, login, password);
        user.setSalt(salt);
        user.setId(id);
        return user;
    }

    private Set<Role> getRolesFromUserId(Long userId) throws SQLException {
        String query = "SELECT role_name FROM users_roles "
                + "JOIN roles USING (role_id) WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
            return roles;
        }
    }

    private void deleteUserFromUsersRoles(Long userId) throws SQLException {
        String query = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnectionInternetShop()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }
}
