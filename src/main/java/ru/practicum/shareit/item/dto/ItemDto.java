package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * Класс-дто для проектирования данных в сущность <b>Item</b>.
 */
@Data
@AllArgsConstructor
public class ItemDto {
    protected Long id;
    protected String name;
    protected String description;
    protected Boolean available;
    protected User owner;
    protected ItemRequest request;
}
