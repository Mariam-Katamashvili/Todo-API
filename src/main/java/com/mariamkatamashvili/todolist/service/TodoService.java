package com.mariamkatamashvili.todolist.service;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoDTO;
import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;

import java.util.List;

public interface TodoService {
    TodoDTO createTask(String username, TodoInfoDTO todoInfo);
    List<TodoInfoDTO> findTodosByUsername(String username);
    TodoInfoDTO updateTodo(String username, String title, TodoInfoDTO todoInfo);
    void delete(String username, String title);
}