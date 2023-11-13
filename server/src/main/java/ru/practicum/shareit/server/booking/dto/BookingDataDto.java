package ru.practicum.shareit.server.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс-дто для проектирования данных в сущность <b>BookingDataDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDataDto {
    protected Long id;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected Long bookerId;
}