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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

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
            @PathVariable int userId,
            Model model,
            HttpSession session
    ) {
        User user = userService.getUserById(userId);
        User userSession = (User) session.getAttribute("userSession");

        model.addAttribute("user", user);
        model.addAttribute("userSession", userSession);
        return "profil/my-profil.html";
    }

    @PostMapping("/update/{userId}")
    public String updateUserById(@PathVariable int userId, @ModelAttribute User updatedUser, Model model, BindingResult bindingResult, HttpSession session) {
        User userToUpdate = userService.getUserById(userId);
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPhone(updatedUser.getPhone());
        userToUpdate.setStreet(updatedUser.getStreet());
        userToUpdate.setPostalCode(updatedUser.getPostalCode());
        userToUpdate.setCity(updatedUser.getCity());

        // Vérification si le nom d'utilisateur est modifié
        if (!userToUpdate.getUsername().equals(updatedUser.getUsername())) {
            if (!userService.isUsernameUnique(updatedUser.getUsername(), userId)) {
                bindingResult.rejectValue("username", "error.user", "Nom d'utilisateur déjà utilisé");
                model.addAttribute("user", userToUpdate);
                model.addAttribute("errorMessage", "Nom d'utilisateur déjà utilisé");
                return "profil/update-my-profil";  // Retourner sur la même page avec les erreurs
            }
            userToUpdate.setUsername(updatedUser.getUsername());


            // Vérification et mise à jour du mot de passe
            if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty()
                    && updatedUser.getConfirmationPassword() != null && !updatedUser.getConfirmationPassword().isEmpty()) {

                if (updatedUser.getPassword() != null && encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())
                        && updatedUser.getNewPassword().equals(updatedUser.getConfirmationPassword())) {
                    userToUpdate.setPassword(encoder.encode(updatedUser.getNewPassword()));
                } else if (!encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())) {
                    bindingResult.rejectValue("password", "error.user", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                    model.addAttribute("user", userToUpdate);
                    model.addAttribute("errorMessage", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                    return "profil/update-my-profil";  // Retourner sur la même page avec les erreurs
                } else if (!Objects.equals(updatedUser.getConfirmationPassword(), updatedUser.getNewPassword())) {
                    bindingResult.rejectValue("password", "error.user", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                    model.addAttribute("user", userToUpdate);
                    model.addAttribute("errorMessage", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                    return "profil/update-my-profil";
                }
                userService.updateUser(userToUpdate);

                session.invalidate();

                return "redirect:/login";
            }

            userService.updateUser(userToUpdate);

            return "redirect:/logout";
        }
        // Vérification et mise à jour du mot de passe
        if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty()
                && updatedUser.getConfirmationPassword() != null && !updatedUser.getConfirmationPassword().isEmpty()) {

            if (updatedUser.getPassword() != null && encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())
                    && updatedUser.getNewPassword().equals(updatedUser.getConfirmationPassword())) {
                userToUpdate.setPassword(encoder.encode(updatedUser.getNewPassword()));
            } else if (!encoder.matches(updatedUser.getPassword(), userToUpdate.getPassword())) {
                bindingResult.rejectValue("password", "error.user", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                model.addAttribute("user", userToUpdate);
                model.addAttribute("errorMessage", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                return "profil/update-my-profil";  // Retourner sur la même page avec les erreurs
            } else if (!Objects.equals(updatedUser.getConfirmationPassword(), updatedUser.getNewPassword())) {
                bindingResult.rejectValue("password", "error.user", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                model.addAttribute("user", userToUpdate);
                model.addAttribute("errorMessage", "Mot de passe actuel incorrect ou la confirmation du mot de passe ne correspond pas");
                return "profil/update-my-profil";  // Retourner sur la même page avec les erreurs
            }
            userService.updateUser(userToUpdate);

            session.invalidate();

            return "redirect:/login";
        }

        userService.updateUser(userToUpdate);

        return "redirect:/session";
    }

    @GetMapping("/redirect/{userId}")
    public String redirectToUpdateProfilePage(
            @PathVariable int userId,
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("userSession");
        model.addAttribute("user", user);
        return "profil/update-my-profil.html";
    }

    @GetMapping("/update/{userId}")
    public String showUpdateForm(@PathVariable int userId, Model model, HttpSession session) {
        User userToUpdate = (User) session.getAttribute("userSession");
        model.addAttribute("user", userToUpdate);
        return "redirect:/user/" + userId;
    }

    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable int userId, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("userSession");
        if (user != null && user.getUserId() == userId) {
            userService.deleteUserById(userId);
            // Supprimer l'utilisateur de la session
            session.invalidate();
            redirectAttributes.addFlashAttribute("message", "Votre compte a été supprimé avec succès.");
            return "redirect:/login?deleteSuccess";
        } else {
            return "redirect:/error"; // ou une autre page de votre choix
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/security/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}
