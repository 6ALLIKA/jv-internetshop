package ma.internetshop.dao;

import java.util.Optional;
import ma.internetshop.model.User;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> getByLogin(String login);
}
