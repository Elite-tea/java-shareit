package ru.practicum.shareit.server.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-дто для проектирования данных в сущность <b>ItemDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    protected Long id;
    protected String name;
    protected String description;
    protected Boolean available;
    protected Long requestId;
}