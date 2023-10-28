package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс-дто для проектирования данных в сущность <b>User</b>.
 */
@Data
@AllArgsConstructor
public class UserDto {
    protected Long id;
    protected String name;
    protected String email;
}
