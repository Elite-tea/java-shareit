package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;

public interface ItemStorage {
    HashMap<Long, Item> getItems();

    Long getId();
    /**
     * Метод добавления вещи
     *
     * @param itemDto объект пользователя
     * @return возвращает созданного пользователя
     */
    Item create(ItemDto itemDto, User user);

    /**
     * Метод обновления вещи
     *
     * @param item объект вещи
     * @return возвращает обновленную вещь
     */
    Item update(ItemDto item, Long id, Long userId, Item initItem);

    Item getItemById(Long id);

    Collection<Item> getItemByUser(Long id);

    Collection<Item> getItemBySearch(String text);
}
