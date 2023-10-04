package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

/**
 * Класс-маппер для трансформации данных в сущность.
 */
public class ItemMapper {
    /**
     * Метод преобразовывает данные в сущность <b>Item</b>
     */
        public static Item toDtoItem(ItemDto itemDto, User user) {
            return new Item(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.getAvailable(),
                    user,
                    itemDto.getRequest() != null ? itemDto.getRequest() : null
            );
        }

    /**
     * Метод преобразовывает данные в сущность <b>Item</b> для обновления данных сущности.
     */
    public static Item dtoItemUpdate(ItemDto itemDto, Item item) {
        return new Item(
                itemDto.getId() != null ? itemDto.getId() : item.getId(),
                itemDto.getName() != null ? itemDto.getName() : item.getName(),
                itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription(),
                itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable(),
                itemDto.getOwner() != null ? itemDto.getOwner() : item.getOwner(),
                itemDto.getRequest() != null ? itemDto.getRequest() : null
        );
    }
    }