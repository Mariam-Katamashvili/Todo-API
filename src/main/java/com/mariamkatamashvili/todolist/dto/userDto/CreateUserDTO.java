package com.mariamkatamashvili.todolist.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateUserDTO {
    private String username;
    private String firstName;
    private String lastName;
}
