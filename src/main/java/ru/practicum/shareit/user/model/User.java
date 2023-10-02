package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * Класс модель, описывающий структуру сущности <b>User</b>
 */
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    @Email
    private String email;
}
