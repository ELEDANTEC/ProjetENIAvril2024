package fr.eni.projeteniavril2024.bll.impl;

import fr.eni.projeteniavril2024.bll.UserService;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.UserDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public void updateUserById(User user) {
        userDAO.updateUserById(user);
    }


    public boolean isUniqueUsername(User user) {
        User existingUser = userDAO.findByUsername(user.getUsername());
        return existingUser == null || existingUser.getUserId() == user.getUserId();
    }

    public boolean isUniqueEmail(User user) {
        User existingUser = userDAO.findByEmail(user.getEmail());
        return existingUser == null || existingUser.getUserId() == user.getUserId();
    }


    @Override
    public void createUser(User user) {
        // Vérifier si le nom d'utilisateur est unique
        if (!isUniqueUsername(user)) {
            // Si ce n'est pas le cas, mettre un message dans l'objet User pour l'afficher dans l'HTML
            return;
        }

        // Vérifier si l'adresse e-mail est unique
        if (!isUniqueEmail(user)) {
            // Si ce n'est pas le cas, mettre un message dans l'objet User pour l'afficher dans l'HTML
            return;
        }

        // Si les informations sont uniques, encoder le mot de passe et créer l'utilisateur
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userDAO.createUser(user);
    }
}
