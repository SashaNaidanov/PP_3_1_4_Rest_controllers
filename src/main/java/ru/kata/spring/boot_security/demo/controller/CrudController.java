package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class CrudController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserValidator userValidator;

    @Autowired
    public CrudController(UserService userService, RoleService roleService, UserDetailsServiceImpl userDetailsService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String getUsers(Principal principal, Model model) {
        User user = userDetailsService.findByUsername(principal.getName());
        model.addAttribute("userSession", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getListOfRoles());
        model.addAttribute("emptyUser", new User());
        return "admin_panel";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(name = "userId") Long id) {
        userService.remove(id);
        return "redirect:/admin";
    }
}
