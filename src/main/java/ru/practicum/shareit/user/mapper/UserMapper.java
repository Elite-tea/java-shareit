package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user, User initUser) {
        return new UserDto(
                user.getId() != null ? user.getId() : initUser.getId(),
                user.getName() != null ? user.getName() : initUser.getName(),
                user.getEmail() != null ? user.getEmail() : initUser.getEmail());
    }
    public static User toDtoUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }
    }
