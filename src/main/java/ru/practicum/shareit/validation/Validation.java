package ru.practicum.shareit.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Objects;

/**
 * Утилитарный класс реализующий проверку соответствия данных в полях объектов с типом ItemDto и User
 */
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Validation {

    /**
     * Пустой приватный конструктор для запрета создания экземпляров утилитарного класса
     */
    private Validation() {

    }

    public static void validationRegistrationUser(User user, UserRepository userRepository) {
        User validUser = userRepository.findByEmailContainingIgnoreCase(user.getEmail());
        try {
            if (Objects.equals(validUser.getEmail(), user.getEmail())
                    && !Objects.equals(validUser.getId(), user.getId())) {
                throw new RuntimeException(String.format("Пользователь с email %s уже зарегистрирован", user.getEmail()));
            }
        } catch (NullPointerException ignore) { }
    }

    public static void validationItem(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidationException(String.format("У предмета %s нет названия", itemDto.getId()));
        }

        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException(String.format("У предмета %s нет описания", itemDto.getId()));
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException(String.format("У предмета %s нет статуса доступности", itemDto.getId()));
        }
    }
}