package ru.practicum.shareit.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Map;

/**
 * Утилитарный класс реализующий проверку соответствия данных в полях объектов с типом Film и User
 */
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Validation {

    /**
     * Пустой приватный конструктор для запрета создания экземпляров утилитарного класса
     */
    private Validation() {

    }


    /**
     * Проверка пользователя на корректность.
     *
     * @param user объект для проверки.
     */
    public static void validationUser(User user, Map<Long, User> map) {

        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException(String.format("Не верный email у пользователя %s", user.getId()));
        }
        validationRegistrationUser(user, map);
    }

    public static void validationRegistrationUser(User user, Map<Long, User> map) {
        long bound = map.size();
        for (long i = 1; i <= bound; i++) {
            if (map.get(i).getEmail().equals(user.getEmail()) && !map.get(user.getId()).getEmail().equals(user.getEmail())) {
                throw new RuntimeException(String.format("Пользователь с email %s уже зарегистрирован", user.getEmail()));
            }
        }
    }

    public static void validationItem(ItemDto itemDto, User user) {
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

    public static void validationUpdateItem(Long userId, Item item) {
        if (userId == null) {
            throw new ValidationException("Отсутствует заголовок X-Sharer-User-Id");
        }
        if (item.getOwner() == null || !userId.equals(item.getOwner().getId())) {
            throw new NotFoundException(String.format(
                    "Доступ запрещен. Попытка пользователем %d изменить чужой предмет %d", userId, item.getId()
            ));
        }
    }
}
