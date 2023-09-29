package ru.practicum.shareit.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.storage.UserStorage;

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

}
