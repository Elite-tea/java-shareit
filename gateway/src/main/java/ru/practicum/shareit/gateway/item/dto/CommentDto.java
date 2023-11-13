package ru.practicum.shareit.gateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-дто для проектирования данных в сущность <b>CommentDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    protected Long id;
    protected String text;
}
