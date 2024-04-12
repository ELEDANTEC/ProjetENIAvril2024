package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bll.ContextService;
import fr.eni.projeteniavril2024.bo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@SessionAttributes({"memberSession"})
public class LoginController {
    private final ContextService contextService;

    LoginController(ContextService contextService) {
        this.contextService = contextService;

    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "security/login.html";
    }

    @GetMapping("/session")
    public String session(
            @ModelAttribute("memberSession") User userSession,
            Principal principal
    ) {
        String username = principal.getName();
        User user = contextService.load(username);
        userSession.setUserId(user.getUserId());
        userSession.setFirstName(user.getFirstName());
        userSession.setLastName(user.getLastName());
        userSession.setAdministrator(user.isAdministrator());
        userSession.setUsername(user.getUsername());

        return "redirect:/";
    }

    @ModelAttribute("memberSession")
    public User userSession() {
        return new User();
    }

  /*  @PostMapping("/login")
    public String login(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password
    ) {
        return "redirect:/";
    }*/
}
