package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest() != null ? item.getRequest() : null
        );
    }

        public static Item toDtoItem(ItemDto itemDto, User user) {
            return new Item(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.getAvailable(),
                    user,
                    itemDto.getRequest() != null ? itemDto.getRequest() : null
            );
        }

    public static Item toDtoItemUpdate(ItemDto itemDto, Item item) {
        return new Item(
                itemDto.getId() != null ? itemDto.getId() : item.getId(),
                itemDto.getName() != null ? itemDto.getName() : item.getName(),
                itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription(),
                itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable(),
                itemDto.getOwner() != null ? itemDto.getOwner() : item.getOwner(),
                itemDto.getRequest() != null ? itemDto.getRequest() : null
        );
    }
    }