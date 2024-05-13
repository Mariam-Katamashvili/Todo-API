package com.mariamkatamashvili.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "/test-schema.sql")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateTodo_WhenTodoInfoIsValid_ThenReturnIsCreated() throws Exception {
        TodoInfoDTO todoInfo = new TodoInfoDTO();
        todoInfo.setTitle("Task Title");
        todoInfo.setDescription("Task Description");

        String todoInfoJson = objectMapper.writeValueAsString(todoInfo);

        mockMvc.perform(post("/todos/{username}", "jdoe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoInfoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateTodo_WhenTodoUserNotExist_ThenReturnIsBadRequest() throws Exception {
        TodoInfoDTO todoInfo = new TodoInfoDTO();

        String todoInfoJson = objectMapper.writeValueAsString(todoInfo);

        mockMvc.perform(post("/todos/{username}", "NonExistingUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoInfoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindTodosByUsername_WhenUsernameIsValid_ThenReturnTodos() throws Exception {
        mockMvc.perform(get("/todos/{username}", "jdoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void testFindTodosByUsername_WhenUserNotExist_ThenReturnIsNotFound() throws Exception {
        mockMvc.perform(get("/todos/{username}", "NonExistingUser"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTodo_WhenTodoExists_ThenReturnStatusIsOk() throws Exception {
        TodoInfoDTO todoInfo = new TodoInfoDTO();
        todoInfo.setTitle("New Title");
        todoInfo.setDescription("New Description");

        String todoInfoJson = objectMapper.writeValueAsString(todoInfo);

        mockMvc.perform(put("/todos/{username}/{title}", "jdoe", "Task 1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoInfoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(todoInfo.getTitle())))
                .andExpect(jsonPath("$.description", is(todoInfo.getDescription())));
    }

    @Test
    void testUpdateTodo_WhenTodoNotExist_ThenReturnStatusIsNotFound() throws Exception {
        TodoInfoDTO todoInfo = new TodoInfoDTO();

        String todoInfoJson = objectMapper.writeValueAsString(todoInfo);

        mockMvc.perform(put("/todos/{username}/{title}", "jdoe", "Task 99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoInfoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTodo_WhenTodoExists_ThenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/todos/{username}/{title}", "jdoe", "Task 1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTodo_WhenTodoNotExist_ThenReturnStatusNotFound() throws Exception {
        mockMvc.perform(delete("/todos/{username}/{title}", "jdoe", "Task 99"))
                .andExpect(status().isNotFound());
    }
}