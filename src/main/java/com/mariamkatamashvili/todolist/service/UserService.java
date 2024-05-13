package com.mariamkatamashvili.todolist.service;

import com.mariamkatamashvili.todolist.dto.userDto.CreateUserDTO;
import com.mariamkatamashvili.todolist.dto.userDto.UserDTO;

public interface UserService {
    UserDTO create (CreateUserDTO createUser);
}
