package ru.practicum.shareit.user.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validation.Validation;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Класс-хранилище для хранения и обновления пользователей
 */
@Getter
@Component()
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    /**
     * Поле хранилище пользователей
     */
    private final HashMap<Long, User> users = new HashMap<>();
    /**
     * Поле счетчик идентификаторов пользователей
     */
    private Long id = 1L;

    /**
     * Метод добавления пользователя.
     *
     * @param user информация о пользователе.
     * @return возвращает созданного пользователя
     * @throws NotFoundException генерирует 404 ошибку в случае если пользователь с электронной почтой уже зарегистрирован.
     */
    public User create(@Valid @RequestBody User user) {
        user.setId(id);
        log.debug("Пользователь создан");
        users.put(id, user);
        id++;
        return user;
    }

    /**
     * Метод удаления пользователя.
     *
     * @param id идентификатор пользователя.
     * @throws ValidationException генерирует 400 ошибку в случае если пользователь не существует
     */
    @Override
    public void deleteUser(@Valid @RequestBody Long id) {
        if (users.containsKey(id)) {
            log.debug("Пользователь удален");
            users.remove(id);
        } else {
            throw new ValidationException(String.format("Пользователь с id %s не существует.", id));
        }
    }

    /**
     * Метод обновления пользователя.
     *
     * @param user информация о пользователе.
     * @return возвращает обновленного пользователя
     * @throws NotFoundException генерирует 404 ошибку в случае если пользователя не существует.
     */
    public User update(@Valid @RequestBody User user, @PathVariable Long id) {
        User initUser;
        User updateUser;
        if (users.containsKey(id)) {
            initUser = users.get(id);
            UserDto userDto = UserMapper.toUserDto(user, initUser);
            updateUser = UserMapper.dtoToUser(userDto);
            Validation.validationRegistrationUser(updateUser, users);
            log.debug("Пользователь обновлен");
            users.put(id, updateUser);
        } else {
            log.debug("Пользователь не существует");
            throw new NotFoundException(String.format("Пользователя с id %d не существует", id));
        }
        return updateUser;
    }

    /**
     * Получение списка пользователей.
     *
     * @return users возвращает коллекцию пользователей.
     */
    public Collection<User> getUser() {
        log.debug("Запрошен список пользователей, их количество: {}", users.size());
        return users.values();
    }

    /**
     * Метод получение пользователя по id.
     *
     * @param id айди пользователя
     * @return возвращает пользователя с указанным id.s
     * @throws NotFoundException генерирует 404 ошибку в случае если пользователя не существует.
     */

    @Override
    public User getUserById(Long id) {
        if (users.containsKey(id)) {
            log.debug("Запрошен пользователь c id: {}", id);
            return users.get(id);
        } else {
            log.debug("Пользователь не существует");
            throw new NotFoundException(String.format("Пользователя с id %d не существует", id));
        }
    }
}
