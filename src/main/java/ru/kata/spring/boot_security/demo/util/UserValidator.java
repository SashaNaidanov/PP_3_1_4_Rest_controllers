package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import java.util.Objects;

@Component
public class UserValidator implements Validator {

    private final UserDetailsServiceImpl userDetailsService;

    public UserValidator(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User userFromAddForm = (User) target;
        User userFromDataBase = userDetailsService.findByUsername(userFromAddForm.getUsername());
        if (userFromDataBase != null && !(Objects.equals(userFromAddForm.getId(), userFromDataBase.getId()))) {
            errors.rejectValue("username", "", "User with this username has already exist");
        }
    }
}
