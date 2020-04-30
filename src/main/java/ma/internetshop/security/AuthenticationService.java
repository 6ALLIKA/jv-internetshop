package ma.internetshop.security;

import ma.internetshop.model.User;

public interface AuthenticationService {
    User login(String login, String password);
}
