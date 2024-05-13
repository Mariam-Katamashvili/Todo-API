package com.mariamkatamashvili.todolist.service.implementation;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoDTO;
import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import com.mariamkatamashvili.todolist.dto.userDto.UserInfoDTO;
import com.mariamkatamashvili.todolist.entity.Todo;
import com.mariamkatamashvili.todolist.entity.User;
import com.mariamkatamashvili.todolist.exception.TodoException;
import com.mariamkatamashvili.todolist.mapper.TodoMapper;
import com.mariamkatamashvili.todolist.repository.TodoRepository;
import com.mariamkatamashvili.todolist.repository.UserRepository;
import com.mariamkatamashvili.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoDTO createTask(String username, TodoInfoDTO todoInfo) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new TodoException("User not found", "404");
        }

        if (todoExists(todoInfo.getTitle(), username)) {
            throw new TodoException("Todo already exists", "400");
        }

        UserInfoDTO userInfo = UserInfoDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        Todo todo = Todo
                .builder()
                .title(todoInfo.getTitle())
                .description(todoInfo.getDescription())
                .user(user)
                .build();
        todo = todoRepository.save(todo);

        return TodoDTO
                .builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .user(userInfo)
                .build();
    }

    @Override
    public List<TodoInfoDTO> findTodosByUsername(String username) {
        if (!usernameExists(username)) {
            throw new TodoException("User not found", "404");
        }
        List<Todo> todos = todoRepository.findByUserUsername(username);
        List<TodoInfoDTO> todoInfos = new ArrayList<>();

        todos
                .forEach(todo -> todoInfos.add(todoMapper.todoEntityToTodoInfoDto(todo)));

        return todoInfos;
    }

    @Override
    public TodoInfoDTO updateTodo(String username, String title, TodoInfoDTO todoInfo) {
        Todo todo = todoRepository.findByTitleAndUsername(title, username).orElse(null);

        if (todo == null) {
            throw new TodoException("Todo not found", "404");
        }

        todo.setTitle(todoInfo.getTitle());
        todo.setDescription(todoInfo.getDescription());
        todo = todoRepository.save(todo);
        return todoMapper.todoEntityToTodoInfoDto(todo);
    }

    @Override
    public void delete(String username, String title) {
        Todo todo = todoRepository.findByTitleAndUsername(title, username).orElse(null);
        if (todo == null) {
            throw new TodoException("Todo not found", "404");
        }
        todoRepository.delete(todo);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean todoExists(String title, String username) {
        return todoRepository.findByTitleAndUsername(title, username).isPresent();
    }
}