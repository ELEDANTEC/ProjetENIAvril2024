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

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder encoder;

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
}