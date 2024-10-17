package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.Convertor;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNotUpdatedException;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class CrudController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserValidator userValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public CrudController(UserService userService, RoleService roleService, UserValidator userValidator, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream()
                .map(user -> Convertor.convertToUserDTO(user, modelMapper)).collect(Collectors.toList()));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRoles() {
        return ResponseEntity.ok(roleService.getListOfRoles());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(Convertor.convertToUserDTO(userService.getUserById(id), modelMapper));
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        User user = Convertor.convertToUser(userDTO, modelMapper);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new UserNotCreatedException(getErrorMessage(bindingResult));
        }
        userService.add(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        User user = Convertor.convertToUser(userDTO, modelMapper);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new UserNotUpdatedException(getErrorMessage(bindingResult));
        }
        userService.add(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> removeUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    private static String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append("-").append(error.getDefaultMessage())
                    .append("\n");
        }
        return errorMessage.toString();
    }
}