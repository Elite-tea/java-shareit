package ru.practicum.shareit.server.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс-исключение для генерации ошибки 400.
 */
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
        log.error(message);
    }
}