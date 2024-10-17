package ru.kata.spring.boot_security.demo.util;

import org.modelmapper.ModelMapper;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

public class Convertor {

    public static User convertToUser(UserDTO userDTO, ModelMapper modelMapper) {
        return modelMapper.map(userDTO, User.class);
    }

    public static UserDTO convertToUserDTO(User user, ModelMapper modelMapper) {
        return modelMapper.map(user, UserDTO.class);
    }

}
