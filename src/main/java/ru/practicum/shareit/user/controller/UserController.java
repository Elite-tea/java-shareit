package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 * Класс-контроллер для создания и редактирования пользователей, а так же реализации API со свойством <b>userService</b>.
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    /**
     * Поле сервис
     */
    private final UserService userService;

    /**
     * Добавляет пользователя в хранилище.
     *
     * @param user объект пользователя.
     * @return возвращает добавленного пользователя.
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.getUserStorage().create(user);
    }

    /**
     * Обновляет пользователя в хранилище.
     *
     * @param user объект пользователя.
     * @return возвращает измененного пользователя.
     */
    @PatchMapping("{id}")
    public User update(@Valid @RequestBody User user, @PathVariable Long id) {
        return userService.getUserStorage().update(user, id);
    }

    /**
     * Удаляет пользователя.
     *
     * @param id       id пользователя.
     */
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.getUserStorage().deleteUser(id);
    }

    /**
     * Запрашивает коллекцию пользователей.
     *
     * @return возвращает коллекцию пользователей.
     */
    @GetMapping
    public Collection<User> getUser() {
        return userService.getUserStorage().getUser();
    }

    /**
     * Запрашивает пользователя по id.
     *
     * @param id id пользователя.
     * @return возвращает пользователя c указанным id.
     */
    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserStorage().getUserById(id);
    }
}
