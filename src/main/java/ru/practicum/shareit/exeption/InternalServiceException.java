package ru.practicum.shareit.exeption;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс-исключение для генерации ошибки 500.
 */
@Slf4j
public class InternalServiceException extends RuntimeException {

    public InternalServiceException(final String message) {
        super(message);
        log.error(message);
    }
}
