package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /**
     * Добавляет аренду в бд.
     *
     * @param booking объект аренды.
     * @return возвращает аренду пользователя.
     */
    @PostMapping
    public Booking create(@Valid @RequestBody BookingDto booking, @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingService.create(booking,id);
    }

    /**
     * Обновляет аренду в бд.
     *
     * @param bookingId идентификатор аренды
     * @param userId идентификатор пользователя - инициатора
     * @param approved статус аренды
     * @return возвращает измененную аренду.
     */
    @PatchMapping("{bookingId}")
    public Booking update(@Valid @PathVariable Long bookingId,
                          @RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestParam Boolean approved) {

        return bookingService.update(bookingId, userId, approved);
    }

    /**
     * Аренды пользователя.
     *
     * @param state фильтр статуса аренд
     * @param id идентификатор пользователя - инициатора
     * @return возвращает аренды пользователя.
     */
    @GetMapping
    public Collection<Booking> getAllBookingByUser(@Valid @RequestHeader("X-Sharer-User-Id") Long id,
                                                   @RequestParam(defaultValue = "ALL") BookingStatus state,
                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        return bookingService.getAllBookingByUser(id, state, from, size);
    }

    /**
     * Обновляет аренду в бд.
     *
     * @param bookingId идентификатор аренды
     * @param userId идентификатор пользователя - инициатора
     * @return возвращает измененную аренду.
     */
    @GetMapping("{bookingId}")
    public Booking getBookingByUser(@Valid @PathVariable Long bookingId,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {

        return bookingService.getBookingByUser(bookingId, userId);
    }

    /**
     * Аренды пользователя.
     *
     * @param state фильтр статуса аренд
     * @param id идентификатор пользователя - инициатора
     * @return возвращает аренды пользователя.
     */
    @GetMapping("/owner") //Если есть вещь, нужно найти брони и выдать
    public Collection<Booking> getAllBookingItemByUser(@Valid @RequestHeader("X-Sharer-User-Id") Long id,
                                                       @RequestParam(defaultValue = "ALL") BookingStatus state,
                                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                       @Positive @RequestParam(defaultValue = "10") Integer size) {
        return bookingService.getAllBookingItemByUser(id, state, from, size);
    }
    }