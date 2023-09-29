package ru.practicum.shareit.item.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    /**
     * Поле хранилище вещей
     * -- GETTER --
     *  Получение доступа к хранилищу через сервис.

     */
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;


}