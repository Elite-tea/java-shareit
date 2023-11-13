package ru.practicum.shareit.server.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.server.item.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemGetRequestDTO {
    protected Long id;
    protected String description;
    protected LocalDateTime created;
    protected List<Item> items;
}
