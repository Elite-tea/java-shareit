package ru.practicum.shareit.server.booking;

/**
 * Статусы бронирования
 */
public enum BookingStatus {
    WAITING,
    APPROVED,
    REJECTED,
    ALL,
    PAST,
    FUTURE,
    CURRENT,
    UNSUPPORTED_STATUS
}
