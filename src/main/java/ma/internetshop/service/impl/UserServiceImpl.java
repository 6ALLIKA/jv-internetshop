package ma.internetshop.service.impl;

import java.util.List;
import ma.internetshop.dao.UserDao;
import ma.internetshop.lib.Inject;
import ma.internetshop.lib.Service;
import ma.internetshop.model.User;
import ma.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }
}
