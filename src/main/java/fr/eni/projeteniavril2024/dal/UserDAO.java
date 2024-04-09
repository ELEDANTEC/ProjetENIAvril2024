package fr.eni.projeteniavril2024.dal;

import fr.eni.projeteniavril2024.bo.User;

public interface UserDAO {
    long create(User user);
    long update(User user);
}
