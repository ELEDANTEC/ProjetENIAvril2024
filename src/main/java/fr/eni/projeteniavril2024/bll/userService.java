package fr.eni.tp.projeteniavril2024.bll;

import fr.eni.projeteniavril2024.bo.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}