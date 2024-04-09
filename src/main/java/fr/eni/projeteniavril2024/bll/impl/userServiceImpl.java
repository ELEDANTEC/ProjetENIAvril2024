package fr.eni.tp.projeteniavril2024.bll.impl;

import fr.eni.tp.projeteniavril2024.bo.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    // Simulated database
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(int userId) {
        return users.get(userId);
    }

    @Override
    public void updateUser(User updatedUser) {
        users.put(updatedUser.getUserId(), updatedUser);
    }

    @Override
    public void createUser(User newUser) {
        // Générer un nouvel ID pour le nouvel utilisateur (simulation)
        int newUserId = users.size() + 1;
        newUser.setUserId(newUserId);
        // Ajouter le nouvel utilisateur à la base de données (simulation)
        users.put(newUserId, newUser);
    }
}