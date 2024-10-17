package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class UserDTO {
    private Long id;

    @NotEmpty(message = "Name shouldn't be empty")
    private String firstName;

    @NotEmpty(message = "Last name shouldn't be empty")
    private String lastName;

    @NotEmpty(message = "Email shouldn't be empty")
    private String email;

    @NotEmpty(message = "Username shouldn't be empty")
    private String username;

    private String password;

    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
