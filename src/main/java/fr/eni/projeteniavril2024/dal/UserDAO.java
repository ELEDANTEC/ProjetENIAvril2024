package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;

public interface UserDAO {
    User findById(int id);
    User findByEmail(String email);
    User findByUsername(String username);
}
