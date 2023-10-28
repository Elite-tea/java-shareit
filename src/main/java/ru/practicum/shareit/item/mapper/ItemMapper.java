package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.dto.ItemDataDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.util.List;

public class ItemMapper {
    /**
     * Метод преобразовывает данные в сущность <b>Item</b>
     * @param itemDto полученная сущность
     * @param user пользователь, добавивший предмет
     * @return возвращает вещь для добавления в бд
     */
        public static Item toDtoItem(ItemDto itemDto, User user) {
            return new Item(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.getAvailable(),
                    user,
                    null
            );
        }

    /**
     * Метод преобразовывает данные в сущность <b>Item</b> для обновления данных сущности.
     * @param itemDto сущность с новыми данными для обновления
     * @param item редактируемый предмет
     * @param user пользователь - инициатор обновления
     * @return возвращает вещь для обновления в бд
     */
    public static Item dtoItemUpdate(ItemDto itemDto, Item item, User user) {
        return new Item(
                itemDto.getId() != null ? itemDto.getId() : item.getId(),
                itemDto.getName() != null ? itemDto.getName() : item.getName(),
                itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription(),
                itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable(),
                user,
                item.getRequest()
        );
    }

    /**
     * Метод преобразовывает данные в сущность <b>ItemDataDto</b>
     * @param booking список с последними арендами
     * @param bookingNext список с будущими арендами
     * @param comment комментарии к вещи
     * @return возвращает вещь для выдачи по идентификатору.
     */
    public static ItemDataDto itemToDataDto(List<Booking> booking, List<Booking> bookingNext, List<Comment> comment) {
        return new ItemDataDto(
                booking.get(0).getItem().getId(),
                booking.get(0).getItem().getName(),
                booking.get(0).getItem().getDescription(),
                booking.get(0).getItem().getAvailable(),
                BookingMapper.bookingDtoNoItem(booking.get(0)),
                bookingNext.size() > 1 ? BookingMapper.bookingDtoNoItem(bookingNext.get(bookingNext.size() - 1)) : null,
                comment,
                booking.get(0).getItem().getUser(),
                booking.get(0).getItem().getRequest()
        );
    }

    /**
     * Метод преобразовывает данные в сущность <b>ItemDataDto</b> для выдачи пи условии отсутствия аренд.
     * @param item полученная вещь
     * @param comment комментарии к вещи
     * @return возвращает вещь для выдачи по идентификатору.
     */
    public static ItemDataDto itemToDataDtoNoBooking(Item item,  List<Comment> comment) {
        return new ItemDataDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                null,
                null,
                comment,
                item.getUser(),
                item.getRequest()
        );
    }
    }