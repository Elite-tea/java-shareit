package ru.practicum.shareit.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс-исключение для генерации ошибки 500.
 */
@Slf4j
public class AlreadyExistsException extends RuntimeException {

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<AlreadyExistsException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new AlreadyExistsException("Unknown state: UNSUPPORTED_STATUS"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public AlreadyExistsException(final String message) {
        super(message);
        log.error(message);
    }
}
