package fr.eni.projeteniavril2024.bll.impl;

import fr.eni.projeteniavril2024.bll.UserService;
import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordEncoder encoder;
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
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    public void deleteUserById(int userId) {
        userDAO.deleteUserById(userId);
    }


    public boolean isUniqueUsername(String username) {
        User existingUser = userDAO.findByUsername(username);
        return !(existingUser != null && username.toLowerCase().equals(existingUser.getUsername().toLowerCase()));
    }

    public boolean isUniqueEmail(String email) {
        User existingUser = userDAO.findByEmail(email);
        return !(existingUser != null && email.toLowerCase().equals(existingUser.getEmail().toLowerCase()));
    }


    public void createUser(User user) {
        if (!isUniqueUsername(user.getUsername())) {
            return;
        }

        if (!isUniqueEmail(user.getEmail())) {
            return;
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.createUser(user);
    }

    @Override
    public boolean isUsernameUnique(String username, int userId) {
        User user = userDAO.findByUsername(username);
        return user == null || user.getUserId() == userId;
    }

}

