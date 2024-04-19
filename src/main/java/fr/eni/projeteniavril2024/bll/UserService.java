package fr.eni.projeteniavril2024.bll;

import fr.eni.projeteniavril2024.bo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User getUserById(int userId);
    void createUser(User user);
    void updateUser(User user);
    void deleteUserById(int userId);
    int isUniqueUsername(String username);
}
