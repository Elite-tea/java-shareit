package ru.practicum.shareit.server.user.mapper;

import ru.practicum.shareit.server.user.dto.UserDto;
import ru.practicum.shareit.server.user.entity.User;

/**
 * Класс-маппер для трансформации данных в сущность.
 */
public class UserMapper {
    /**
     * Метод преобразовывает данные в сущность <b>UserDto</b> для последующей трансформации
     */
    public static UserDto toUserDto(UserDto user, User initUser) {
        return new UserDto(
                user.getId() != null ? user.getId() : initUser.getId(),
                user.getName() != null ? user.getName() : initUser.getName(),
                user.getEmail() != null ? user.getEmail() : initUser.getEmail());
    }

    /**
     * Метод преобразовывает данные в сущность <b>User</b>
     */
    public static User dtoToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }
}
