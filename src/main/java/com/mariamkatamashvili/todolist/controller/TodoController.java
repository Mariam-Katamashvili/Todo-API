package com.mariamkatamashvili.todolist.controller;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoDTO;
import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import com.mariamkatamashvili.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/{username}")
    public ResponseEntity<TodoDTO> create(
            @PathVariable("username") String username,
            @RequestBody TodoInfoDTO todoInfo
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTask(username, todoInfo));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<TodoInfoDTO>> findByUsername(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(todoService.findTodosByUsername(username));
    }

    @PutMapping("/{username}/{title}")
    public ResponseEntity<TodoInfoDTO> update(
            @PathVariable String username,
            @PathVariable String title,
            @RequestBody TodoInfoDTO todoInfo
    ) {
        return ResponseEntity.ok(todoService.updateTodo(username, title, todoInfo));
    }

    @DeleteMapping("/{username}/{title}")
    public ResponseEntity<Void> deleteT(
            @PathVariable String username,
            @PathVariable String title
    ) {
        todoService.delete(username, title);
        return ResponseEntity.noContent().build();
    }
}