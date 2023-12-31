package ru.practicum.shareit.server.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.server.user.entity.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
public class ItemRequestDto {
    protected Long id;
    protected String description;
    protected User requestor;
    protected LocalDateTime created;
}
