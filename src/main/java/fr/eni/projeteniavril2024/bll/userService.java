package fr.eni.tp.projeteniavril2024.bll;

import fr.eni.projeteniavril2024.bo.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void updateUser(int userId, User updatedUser) {
        User existingUser = userDAO.getUserById(userId);
        if (existingUser != null) {
            // Mettre à jour les informations de l'utilisateur avec les nouvelles données
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setEmail(updatedUser.getEmail());
            // Continuez avec d'autres champs...

            userDAO.updateUser(existingUser);
        }
    }
}