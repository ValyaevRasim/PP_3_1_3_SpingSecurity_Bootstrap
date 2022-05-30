package spring_boot.service;

import spring_boot.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    void deleteUserById(long id);
    User getUserById (long id);
    List<User> getAllUsers();
    User getUserByUsername(String username);
}
