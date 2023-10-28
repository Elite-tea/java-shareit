package ru.practicum.shareit.item.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.validation.Validation;

@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    /**
     * Поле хранилище вещей
     *  Получение доступа к хранилищу через сервис.
     */
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public Item create(ItemDto itemDto, User user) {
        Validation.validationItem(itemDto, user);
        return itemStorage.create(itemDto, user);
    }

    public Item update(ItemDto item, Long id, Long userId) {
        Item initItem = itemStorage.getItems().get(id);
        Validation.validationUpdateItem(userId, initItem);
        return itemStorage.update(item, id, userId,initItem);
    }
}