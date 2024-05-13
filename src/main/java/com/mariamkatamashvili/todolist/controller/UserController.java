package com.mariamkatamashvili.todolist.controller;

import com.mariamkatamashvili.todolist.dto.userDto.CreateUserDTO;
import com.mariamkatamashvili.todolist.dto.userDto.UserDTO;
import com.mariamkatamashvili.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUser));
    }
}