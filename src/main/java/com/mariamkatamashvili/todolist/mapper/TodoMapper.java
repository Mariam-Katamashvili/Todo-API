package com.mariamkatamashvili.todolist.mapper;

import com.mariamkatamashvili.todolist.dto.todoDto.TodoInfoDTO;
import com.mariamkatamashvili.todolist.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);
    TodoInfoDTO todoEntityToTodoInfoDto(Todo todo);
}