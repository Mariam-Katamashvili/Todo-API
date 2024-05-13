package com.mariamkatamashvili.todolist.service;

import com.mariamkatamashvili.todolist.dto.userDto.CreateUserDTO;
import com.mariamkatamashvili.todolist.dto.userDto.UserDTO;
import com.mariamkatamashvili.todolist.entity.User;
import com.mariamkatamashvili.todolist.exception.TodoException;
import com.mariamkatamashvili.todolist.repository.UserRepository;
import com.mariamkatamashvili.todolist.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @Transactional
    void testCreateUser_WhenUserIsNew_ThenReturnUserDto() {
        String username = "test";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setFirstName("TestFirst");
        user.setLastName("TestLast");
        CreateUserDTO createUser = new CreateUserDTO();
        createUser.setUsername(username);
        createUser.setFirstName("TestFirst");
        createUser.setLastName("TestLast");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = userService.create(createUser);

        assertEquals(userDTO.getUsername(), username);
        assertEquals(userDTO.getFirstName(), createUser.getFirstName());
        assertEquals(userDTO.getLastName(), createUser.getLastName());

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testCreateUser_WhenUserAlreadyExists_ThenThrowException() {
        String username = "test";
        CreateUserDTO createUser = new CreateUserDTO();
        createUser.setUsername(username);
        createUser.setFirstName("TestFirst");
        createUser.setLastName("TestLast");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

        TodoException exception = assertThrows(
                TodoException.class,
                () -> userService.create(createUser)
        );

        assertTrue(exception.getMessage().contains("Username already exists"));
        assertEquals("400", exception.getErrorCode());
    }
}