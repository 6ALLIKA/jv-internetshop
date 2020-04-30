package ma.internetshop.security;

import ma.internetshop.lib.Service;
import ma.internetshop.model.User;
import ma.internetshop.security.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public User login(String login, String password) {
        return null;
    }
}
