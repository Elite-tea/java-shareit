package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
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
    public Collection<Booking> getAllBookingByUser(@Valid @RequestParam(defaultValue = "ALL") String state,
                                                   @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingService.getAllBookingByUser(id, state);
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
     * @return возвращает аренды всех вещей пользователя.
     */
    @GetMapping("/owner") //Если есть вещь, нужно найти брони и выдать
    public Collection<Booking> getAllBookingItemByUser(@Valid @RequestParam(defaultValue = "ALL") String state,
                                                   @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingService.getAllBookingItemByUser(id, state);
    }
    }