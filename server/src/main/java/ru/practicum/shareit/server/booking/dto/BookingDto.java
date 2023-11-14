package ru.practicum.shareit.server.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс-дто для проектирования данных в сущность <b>BookingDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    protected Long id;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected Long itemId;
}
