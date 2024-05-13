package com.mariamkatamashvili.todolist.dto.userDto;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<TodoInfoDTO> todos;
}
