package fr.eni.projeteniavril2024.bll;

import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.dal.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User getUserById(int userId);

    void updateUser(User user);

    void saveUser(User user);

    void deleteUserById(int userId);

    void createUser(User user);
}
