package ru.practicum.shareit.gateway.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.gateway.user.dto.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
public class ItemRequestDto {
    protected Long id;
    protected String description;
    protected UserDto requestor;
    protected LocalDateTime created;
}
