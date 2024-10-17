package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.util.Convertor;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService, ModelMapper modelMapper) {
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/sessionUser")
    public ResponseEntity<UserDTO> getSessionUser(Principal principal) {
        return ResponseEntity.ok(Convertor.convertToUserDTO(userDetailsService.findByUsername(principal.getName()), modelMapper));
    }
}