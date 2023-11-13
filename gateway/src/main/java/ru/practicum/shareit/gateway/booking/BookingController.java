package ru.practicum.shareit.gateway.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.booking.dto.BookingDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestBody @Valid BookingDto requestDto) {
        log.info("Creating booking {}, userId={}", requestDto, userId);
        return bookingClient.create(userId, requestDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long bookingId,
                                         @RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam Boolean approved) {

        return bookingClient.update(userId, bookingId, approved);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingByUser(@RequestHeader("X-Sharer-User-Id") Long id,
                                                      @RequestParam(defaultValue = "ALL") BookingStatus state,
                                                      @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get booking with state {}, userId={}, from={}, size={}", state, id, from, size);
        return bookingClient.getAllBookingByUser(id, state, from, size);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingByUser(@Valid @PathVariable Long bookingId,
                                                   @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBookingByUser(userId, bookingId);
    }

    @GetMapping("/owner") //Если есть вещь, нужно найти брони и выдать
    public ResponseEntity<Object> getAllBookingItemByUser(@Valid @RequestHeader("X-Sharer-User-Id") Long id,
                                                          @RequestParam(defaultValue = "ALL") BookingStatus state,
                                                          @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                          @Positive @RequestParam(defaultValue = "10") Integer size) {
        return bookingClient.getAllBookingItemByUser(id, state, from, size);
    }
}