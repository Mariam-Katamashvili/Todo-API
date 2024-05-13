package com.mariamkatamashvili.todolist.service.implementation;

import com.mariamkatamashvili.todolist.dto.userDto.CreateUserDTO;
import com.mariamkatamashvili.todolist.dto.userDto.UserDTO;
import com.mariamkatamashvili.todolist.entity.User;
import com.mariamkatamashvili.todolist.exception.TodoException;
import com.mariamkatamashvili.todolist.repository.UserRepository;
import com.mariamkatamashvili.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDTO create(CreateUserDTO createUser) {
        checkCreateUserDto(createUser);

        if (usernameExists(createUser.getUsername())) {
            throw new TodoException("Username already exists", "400");
        }
        User user = new User();
        user.setUsername(createUser.getUsername());
        user.setFirstName(createUser.getFirstName());
        user.setLastName(createUser.getLastName());
        user =  userRepository.save(user);

        return UserDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .todos(new ArrayList<>())
                .build();
    }

    private static void checkCreateUserDto(CreateUserDTO createUserDto) {
        String notValidRegistration = "Not valid registration";

        if (createUserDto.getUsername() == null || createUserDto.getUsername().isBlank()) {
            throw new TodoException(notValidRegistration, "400");
        }
        if (createUserDto.getFirstName() == null || createUserDto.getFirstName().isBlank()) {
            throw new TodoException(notValidRegistration, "400");
        }
        if (createUserDto.getLastName() == null || createUserDto.getLastName().isBlank()) {
            throw new TodoException(notValidRegistration, "400");
        }
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}