package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    void create(User user);
    void update(User user);
    void updateCredit(int userId, int credit);
    void deleteById(int userId);
    int existingUser(int userId);
    int isUniqueUsername(String username);
    int isUniqueEmail(String email);
}
