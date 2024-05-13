package com.mariamkatamashvili.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariamkatamashvili.todolist.dto.userDto.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = "/test-schema.sql")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateUser_WhenCreateUserDtoValid_ThenReturnIsCreated() throws Exception {
        CreateUserDTO createUser = new CreateUserDTO();
        createUser.setUsername("username");
        createUser.setFirstName("first");
        createUser.setLastName("last");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUser)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateUser_WhenCreateUserDtoInvalid_ThenReturnBadRequest() throws Exception {
        CreateUserDTO createUser = new CreateUserDTO();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUser)))
                .andExpect(status().isBadRequest());
    }
}