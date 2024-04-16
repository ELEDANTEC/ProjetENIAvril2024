package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bll.UserService;
import fr.eni.projeteniavril2024.bll.impl.UserServiceImpl;
import fr.eni.projeteniavril2024.bo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private PasswordEncoder encoder;

    public UserController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public String getUserById(
            @ModelAttribute("userSession") User userSession,
            @PathVariable int userId,
            Model model
    ) {
        User user = userService.getUserById(userId);

        model.addAttribute("user", user);
        return "profil/my-profil.html";
    }

    @PostMapping("/update/{userId}")
    public String updateUserById(@PathVariable int userId, @ModelAttribute User updatedUser, Model model, BindingResult bindingResult) {
        User userToUpdate = userService.getUserById(userId);

        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPhone(updatedUser.getPhone());
        userToUpdate.setStreet(updatedUser.getStreet());
        userToUpdate.setPostalCode(updatedUser.getPostalCode());
        userToUpdate.setCity(updatedUser.getCity());

        // Vérification et mise à jour du mot de passe
        if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty()
                && updatedUser.getConfirmationPassword() != null && !updatedUser.getConfirmationPassword().isEmpty()
                && updatedUser.getPassword() != null && encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())
                && updatedUser.getNewPassword().equals(updatedUser.getConfirmationPassword())) {
            userToUpdate.setPassword(encoder.encode(updatedUser.getNewPassword()));
        } else if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty()
                && updatedUser.getConfirmationPassword() != null && !updatedUser.getConfirmationPassword().isEmpty()
                && !encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())) {
            bindingResult.rejectValue("password", "error.user", "Mot de passe incorrect");
            return "profil/update-my-profil";  // Correction du chemin du template
        }

        userService.updateUser(userToUpdate);
        model.addAttribute("user", userToUpdate);
        return "redirect:/user/" + userId;
    }

    @GetMapping("/update/{userId}")
    public String showUpdateForm(@PathVariable int userId, Model model) {
        User userToUpdate = userService.getUserById(userId);
        model.addAttribute("user", userToUpdate);
        return "redirect:/user/" + userId;
    }

    @GetMapping("/redirect/{userId}")
    public String redirectToUpdateProfilePage(
            @PathVariable int userId,
            Model model
    ) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "profil/update-my-profil.html";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable int userId, HttpSession session) {
        userService.deleteUserById(userId);

        // Supprimer l'utilisateur de la session
        session.removeAttribute("userSession");

        return "redirect:/login?deleteSuccess";
    }

    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return "redirect:/";
    }
}
