package ma.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import ma.internetshop.dao.UserDao;
import ma.internetshop.db.Storage;
import ma.internetshop.lib.Dao;
import ma.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(x -> user.getId().equals(Storage.users.get(x).getId()))
                .forEach(i -> Storage.users.set(i, user));
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Storage.users
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }
}
