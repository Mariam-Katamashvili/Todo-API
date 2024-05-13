package com.mariamkatamashvili.todolist.mapper;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import com.mariamkatamashvili.todolist.entity.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoInfoDTO todoEntityToTodoInfo(Todo todo);
}