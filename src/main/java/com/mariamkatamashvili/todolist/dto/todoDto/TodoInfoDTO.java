package com.mariamkatamashvili.todolist.dto.todoDto;

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
public class TodoInfoDTO {
    private String title;
    private String description;
}
