package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import java.security.Principal;

@Controller
public class MainController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public MainController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public String pageForInfo(Principal principal, Model model) {
        User user = userDetailsService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user_panel";
    }
}
