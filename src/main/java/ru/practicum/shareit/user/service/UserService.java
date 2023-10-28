package ru.practicum.shareit.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.validation.Validation;

import javax.validation.Valid;
import javax.validation.ValidationException;

@Getter
@Service
@RequiredArgsConstructor
@Slf4j

public class UserService {
    /**
     * Поле хранилище пользователей
     * -- GETTER --
     *  Получение доступа к хранилищу через сервис.

     */
    private final UserStorage userStorage;

    public User create(@Valid @RequestBody User user) {
        Validation.validationUser(user, userStorage.getUsers());
        return userStorage.create(user);
    }

    public void deleteUser(@Valid @RequestBody Long id) {
        if (userStorage.getUsers().containsKey(id)) {
            log.debug("Пользователь удален");
            userStorage.getUsers().remove(id);
        } else {
            throw new ValidationException(String.format("Пользователь с id %s не существует.", id));
        }
    }
}
