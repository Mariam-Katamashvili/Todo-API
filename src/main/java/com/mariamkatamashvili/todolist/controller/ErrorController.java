package com.mariamkatamashvili.todolist.controller;

import com.mariamkatamashvili.todolist.dto.exceptionDto.ErrorDTO;
import com.mariamkatamashvili.todolist.exception.TodoException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order
public class ErrorController {
    @ExceptionHandler(TodoException.class)
    public ResponseEntity<ErrorDTO> handleCreationException(TodoException e) {
        ErrorDTO error = new ErrorDTO();
        error.setErrorCode(e.getErrorCode());
        error.setErrorMessage(e.getMessage());
        HttpStatus status = codeToStatus(error.getErrorCode());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(error);
    }

    private HttpStatus codeToStatus(String errorCode) {
        try {
            int status = Integer.parseInt(errorCode);
            return HttpStatus.resolve(status) != null ?
                    HttpStatus.resolve(status) :
                    HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}