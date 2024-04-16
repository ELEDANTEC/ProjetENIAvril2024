package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User findByEmail(String email);
    User getUserById(int userId);
    User findById(int id);
    User findByUsername(String username);
    void updateUserById(User user);
    void createUser(User user);
    int existingUser(int userId);
}

