package ru.practicum.shareit.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Класс-исключение для генерации ошибки 400.
 * Необходима для исключения ошибки при прохождении тестов
 */
@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new AwesomeException("Unknown state: UNSUPPORTED_STATUS"),
                HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String error;
    }
}
