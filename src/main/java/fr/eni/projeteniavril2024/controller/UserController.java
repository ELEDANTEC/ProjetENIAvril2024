package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bo.User;
import fr.eni.projeteniavril2024.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint pour récupérer un utilisateur par son ID
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable int userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "Profil/MyProfil.html";
    }

}
