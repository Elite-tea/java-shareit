package ru.practicum.shareit.gateway.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс-исключение для генерации ошибки 404.
 */
@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
        log.error(message);
    }
}