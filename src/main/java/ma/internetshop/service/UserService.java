package ma.internetshop.service;

import java.util.Optional;
import ma.internetshop.model.User;

public interface UserService extends GenericService<User, Long> {

    Optional<User> getByLogin(String login);
}
