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

    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User user) {
        userService.updateUser(userId, user);
        return ResponseEntity.ok().build();
    }
}
