package com.mariamkatamashvili.todolist.service;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoDTO;
import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import com.mariamkatamashvili.todolist.entity.Todo;
import com.mariamkatamashvili.todolist.entity.User;
import com.mariamkatamashvili.todolist.exception.TodoException;
import com.mariamkatamashvili.todolist.mapper.TodoMapper;
import com.mariamkatamashvili.todolist.repository.TodoRepository;
import com.mariamkatamashvili.todolist.repository.UserRepository;
import com.mariamkatamashvili.todolist.service.implementation.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TodoMapper todoMapper;
    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    void testCreateTodo_WhenUserNotExist_ThenThrowTodoException() {
        TodoInfoDTO todoInfo = new TodoInfoDTO();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        TodoException exception = assertThrows(
                TodoException.class,
                () -> todoService.createTask("user", todoInfo)
        );

        assertTrue(exception.getMessage().contains("User not found"));
        assertEquals("404", exception.getErrorCode());

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testCreateTodo_WhenUserExist_ThenReturnTodoDto() {
        User user = new User();
        user.setUsername("user");
        user.setFirstName("first");
        user.setLastName("last");
        user.setTodos(new ArrayList<>());

        TodoInfoDTO todoInfo = new TodoInfoDTO();
        todoInfo.setTitle("title");
        todoInfo.setDescription("description");

        Todo todo = Todo
                .builder()
                .id(1L)
                .title("title")
                .description("description")
                .user(user)
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(todoRepository.findByTitleAndUsername(anyString(), anyString())).thenReturn(Optional.empty());
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoDTO todoDTO = todoService.createTask("user", todoInfo);

        assertEquals("title", todoDTO.getTitle());
        assertEquals("description", todoDTO.getDescription());

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(todoRepository, times(1)).save(any(Todo.class));
        verify(todoRepository, times(1)). findByTitleAndUsername(anyString(), anyString());
    }

    @Test
    void testFindTodosByUsername_WhenUserNotExist_ThenThrowTodoException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        TodoException exception = assertThrows(
                TodoException.class,
                () -> todoService.findTodosByUsername("user")
        );

        assertTrue(exception.getMessage().contains("User not found"));
        assertEquals("404", exception.getErrorCode());

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testFindByUsername_WhenUserExists_ThenReturnListOfTodoInfoDto() {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(todoRepository.findByUserUsername(anyString())).thenReturn(List.of(new Todo(), new Todo()));

        List<TodoInfoDTO> todoInfos = todoService.findTodosByUsername("user");

        assertEquals(2, todoInfos.size());

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testUpdateTodo_WhenTodoNotExist_ThenThrowTodoException() {
        TodoInfoDTO todoInfo = new TodoInfoDTO();
        when(todoRepository.findByTitleAndUsername(anyString(), anyString())).thenReturn(Optional.empty());

        TodoException exception = assertThrows(
                TodoException.class,
                () -> todoService.updateTodo("user", "title", todoInfo)
        );

        assertTrue(exception.getMessage().contains("Todo not found"));
        assertEquals("404", exception.getErrorCode());

        verify(todoRepository, times(1)).findByTitleAndUsername(anyString(), anyString());
    }

    @Test
    void testUpdateTodo_WhenTodoExists_ThenReturnUpdatedTodoInfoDto() {
        Todo todo = Todo.builder()
                .id(1L)
                .title("title")
                .description("description")
                .user(new User())
                .build();

        TodoInfoDTO todoInfo = TodoInfoDTO.builder()
                .title("title2")
                .description("description2")
                .build();

        TodoInfoDTO expected = TodoInfoDTO.builder()
                .title("title2")
                .description("description2")
                .build();

        when(todoRepository.findByTitleAndUsername("title", "user")).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        when(todoMapper.todoEntityToTodoInfoDto(todo)).thenReturn(expected);

        TodoInfoDTO actual = todoService.updateTodo("user", "title", todoInfo);

        assertEquals("title2", actual.getTitle());
        assertEquals("description2", actual.getDescription());

        verify(todoRepository, times(1)).findByTitleAndUsername("title", "user");
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testDeleteTodo_WhenTodoNotExist_ThenThrowTodoException() {
        when(todoRepository.findByTitleAndUsername(anyString(), anyString())).thenReturn(Optional.empty());

        TodoException exception = assertThrows(
                TodoException.class,
                () -> todoService.delete("user", "title")
        );

        assertTrue(exception.getMessage().contains("Todo not found"));
        assertEquals("404", exception.getErrorCode());

        verify(todoRepository, times(1)).findByTitleAndUsername(anyString(), anyString());
    }

    @Test
    void testDeleteTodo_WhenTodoExists_ThenDeleteTodo() {
        Todo todo = new Todo();

        when(todoRepository.findByTitleAndUsername(anyString(), anyString())).thenReturn(Optional.of(todo));

        todoService.delete("user", "title");

        verify(todoRepository, times(1)).findByTitleAndUsername(anyString(), anyString());
        verify(todoRepository, times(1)).delete(todo);
    }
}