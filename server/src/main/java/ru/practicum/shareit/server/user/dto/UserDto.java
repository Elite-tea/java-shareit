package ru.practicum.shareit.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

/**
 * Класс-дто для проектирования данных в сущность <b>UserDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    protected Long id;
    protected String name;
    @Email
    protected String email;
}
