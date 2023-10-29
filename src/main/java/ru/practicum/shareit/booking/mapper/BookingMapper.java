package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDataDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

public class BookingMapper {
    /**
     * Метод преобразовывает данные в сущность <b>BookingDataDto</b> для обновления данных сущности.
     * @param bookingDto аренда для вещи
     * @param user пользователь, инициализирующий аренду
     * @param item вещь, берущую в аренду
     * @param time время создания аренды
     * @return возвращает аренду для вещи
     */
    public static Booking toDtoNewBooking(BookingDto bookingDto, User user, Item item, LocalDateTime time) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart() == null ? time : bookingDto.getStart(),
                bookingDto.getEnd() == null ? time : bookingDto.getEnd(),
                item,
                user,
                BookingStatus.WAITING);
    }

    /**
     * Метод преобразовывает данные в сущность <b>BookingDataDto</b> для обновления данных сущности.
     * @param booking последняя аренда для вещи
     * @return возвращает аренду для вещи с последующей выдачей по идентификатору вещи в
     * {@link ru.practicum.shareit.item.service.ItemService}.
     */
    public static BookingDataDto bookingDtoNoItem(Booking booking) {
        return new BookingDataDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker().getId()
        );
    }
}