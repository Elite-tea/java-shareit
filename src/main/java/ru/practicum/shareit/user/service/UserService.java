package ru.practicum.shareit.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.validation.Validation;

import javax.validation.ConstraintViolationException;

/**
 * Класс-сервис для создания и редактирования пользователей, а так же реализации API.
 */
@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * Добавляет пользователя в бд.
     *
     * @param user объект пользователя.
     * @return возвращает добавленного пользователя.
     */
    public User create(@RequestBody UserDto user) {
        try {
            User newUser = UserMapper.dtoToUser(user);
            return  userRepository.save(newUser);
        } catch (ConstraintViolationException | NullPointerException s) {
            throw new ValidationException(String.format("Не верный email у пользователя %s", user.getId()));
        }
    }

    /**
     * Удаляет пользователя из бд.
     *
     * @param id идентификатор пользователя.
     */
    public void deleteUser(@RequestBody Long id) {
        if (userRepository.findById(id).isPresent()) {
            log.debug("Пользователь удален");
            userRepository.deleteById(id);
        } else {
            throw new ValidationException(String.format("Пользователь с id %s не существует.", id));
        }
    }

    /**
     * Обновляет пользователя в бд.
     *
     * @param user объект пользователя.
     * @param id идентификатор пользователя.
     * @return возвращает добавленного пользователя.
     */
    public User update(User user,Long id) {
        UserDto userDto = UserMapper.toUserDto(user, userRepository.getReferenceById(id));
        User userUpdate = UserMapper.dtoToUser(userDto);
        userUpdate.setId(id);
        Validation.validationRegistrationUser(userUpdate, userRepository);
        userRepository.save(userUpdate);
        return userUpdate;
    }

    /**
     * Запрашивает пользователя по идентификатору
     *
     * @param id идентификатор пользователя.
     * @return возвращает пользователя.
     */
    public User getUser(@PathVariable Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (RuntimeException s) {
            throw new NotFoundException(String.format("Пустой email у пользователя %s", id));
        }
    }
}