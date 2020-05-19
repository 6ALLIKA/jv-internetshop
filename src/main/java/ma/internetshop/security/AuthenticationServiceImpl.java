package ma.internetshop.security;

import ma.internetshop.exceptions.AuthenticationException;
import ma.internetshop.lib.Inject;
import ma.internetshop.lib.Service;
import ma.internetshop.model.User;
import ma.internetshop.service.UserService;
import ma.internetshop.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDB
                = userService
                .getByLogin(login)
                .orElseThrow(() -> new AuthenticationException("Incorrect username or password"));

        if (HashUtil.hashPassword(password, userFromDB.getSalt()).equals(userFromDB.getPassword())) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
