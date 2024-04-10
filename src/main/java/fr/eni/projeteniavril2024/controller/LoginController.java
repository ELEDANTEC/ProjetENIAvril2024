package fr.eni.projeteniavril2024.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "security/login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletResponse response) throws IOException {
    return "redirect:/test";
    }

    @GetMapping("/test")
    public String test() {return "test.html";}

}
