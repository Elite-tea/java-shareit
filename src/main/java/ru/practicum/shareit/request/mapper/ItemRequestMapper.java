package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.request.dto.ItemGetRequestDTO;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.entity.Request;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRequestMapper {
    public static Request dtoToItemRequest(ItemRequestDto itemRequest, User user, LocalDateTime time) {
        return new Request(
                itemRequest.getId(),
                itemRequest.getDescription(),
                user,
                time
        );
    }

    public static ItemGetRequestDTO transformationGetRequestDTO(Request request, List<Item> item) {
        return new ItemGetRequestDTO(request.getId(),
                request.getDescription(),
                request.getCreated(),
                item);
    }
}
