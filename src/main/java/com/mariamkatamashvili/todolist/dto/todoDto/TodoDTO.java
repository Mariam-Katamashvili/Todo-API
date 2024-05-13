package com.mariamkatamashvili.todolist.dto.todoDto;

import com.mariamkatamashvili.todolist.dto.userDto.UserInfoDTO;
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
public class TodoDTO {
    private Long id;
    private String title;
    private String description;
    private UserInfoDTO user;
}
