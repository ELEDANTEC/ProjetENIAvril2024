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
        return userDAO.findAll();
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }

    @Override
    public void createUser(User user) {
        if (userDAO.isUniqueUsername(user.getUsername()) > 0) {
            return;
        }
        if (userDAO.isUniqueEmail(user.getEmail()) > 0) {
            return;
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCredit(500);
        userDAO.create(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUserById(int userId) {
        userDAO.deleteById(userId);
    }

    @Override
    public int isUniqueUsername(String username) {
        return userDAO.isUniqueUsername(username);
    }
}

