package ru.practicum.shareit.gateway.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-дто для проектирования данных в сущность <b>UserDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    protected Long id;
    protected String name;
    protected String email;
}
