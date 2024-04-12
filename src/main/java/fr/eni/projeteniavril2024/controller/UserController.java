package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bll.UserService;
import fr.eni.projeteniavril2024.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public String getUserById(
            @PathVariable int userId,
            Model model
    ) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "profil/my-profil.html";
    }

    @PostMapping("/update/{userId}")
    public String updateUserById(
            @PathVariable int userId,
            @ModelAttribute User updatedUser,
            Model model
    ) {
        // Récupérer l'utilisateur à partir de la base de données
        User userToUpdate = userService.getUserById(userId);

        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPhone(updatedUser.getPhone());
        userToUpdate.setStreet(updatedUser.getStreet());
        userToUpdate.setPostalCode(updatedUser.getPostalCode());
        userToUpdate.setCity(updatedUser.getCity());
        userToUpdate.setPassword(updatedUser.getPassword());
        userService.updateUserById(userToUpdate);
        return "redirect:/user/" + userId;
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/security/login";
    }

    @GetMapping("/test/{userId}")
    public String redirectToUpdateProfilePage(
            @PathVariable int userId,
            Model model
    ) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "profil/update-my-profil.html";
    }
}
