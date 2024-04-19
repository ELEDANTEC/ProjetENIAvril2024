package fr.eni.projeteniavril2024.controller;

import fr.eni.projeteniavril2024.bll.ContextService;
import fr.eni.projeteniavril2024.bo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({"userSession"})
public class LoginController {
    private final ContextService contextService;

    LoginController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("/login")
    public String login() {
        return "security/login.html";
    }

    @GetMapping("/session")
    public String session(
            @ModelAttribute("userSession") User userSession,
            Principal principal
    ) {
        String username = principal.getName();
        User user = contextService.load(username);
        userSession.setUserId(user.getUserId());
        userSession.setUsername(user.getUsername());
        userSession.setLastName(user.getLastName());
        userSession.setFirstName(user.getFirstName());
        userSession.setEmail(user.getEmail());
        userSession.setPhone(user.getPhone());
        userSession.setStreet(user.getStreet());
        userSession.setPostalCode(user.getPostalCode());
        userSession.setCity(user.getCity());
        userSession.setCredit(user.getCredit());
        userSession.setAdministrator(user.isAdministrator());

        HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        httpSession.setMaxInactiveInterval(5 * 60);
        if (httpSession.getAttribute("userSession") == null) {
            return "redirect:/auctions";
        }
        return "redirect:/auctions";
    }

    @ModelAttribute("userSession")
    public User userSession() {
        return new User();
    }
}
