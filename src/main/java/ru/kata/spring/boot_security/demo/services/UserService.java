package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void add(User user) {
        if (user.getId() == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return;
        }
        User userFromDataBase = userRepository.getById(user.getId());
        if (user.getPassword().isEmpty()) {
            user.setPassword(userFromDataBase.getPassword());
            userRepository.save(user);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public User getUserById(Long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            throw new UserNotFoundException("User with this id do not exist!");
        }
        return user;
    }

    @Transactional
    public void remove(Long id) {
        if (userRepository.getById(id) == null) {
            throw new UserNotFoundException("User with this id do not exist!");
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
