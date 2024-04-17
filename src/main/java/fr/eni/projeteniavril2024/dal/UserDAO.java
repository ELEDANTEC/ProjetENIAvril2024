package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(int userId);
    User findById(int id);
    User findByEmail(String email);
    User findByUsername(String username);
    void updateUser(User user);

    void deleteUserById(int userId);

    void saveUser(User user);

    void createUser(User user);
}
