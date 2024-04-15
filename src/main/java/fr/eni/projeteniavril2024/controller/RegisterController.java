package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bll.UserService;
import fr.eni.projeteniavril2024.bo.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"userSession"})
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "security/register.html";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "security/register.html";
        }

        boolean success = userService.createUser(user);

        if (success) {
            model.addAttribute("successMessage", "Votre compte a été créé avec succès !");
            return "login";
        } else {
            ObjectError error = new ObjectError("globalError", "Une erreur s'est produite lors de la création de l'utilisateur.");
            bindingResult.addError(error);
            return "security/register.html";
        }
    }
}
