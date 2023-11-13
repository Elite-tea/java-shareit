package ru.practicum.shareit.gateway.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.gateway.booking.dto.BookingDataDto;
import ru.practicum.shareit.gateway.user.dto.UserDto;

import java.util.List;
import java.util.Objects;

/**
 * Класс-дто для проектирования данных в сущность <b>ItemDataDto</b>.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDataDto {
    protected Long id;
    protected String name;
    protected String description;
    protected Boolean available;
    protected BookingDataDto lastBooking;
    protected BookingDataDto nextBooking;
    protected List<CommentDto> comments;
    protected UserDto user;
    protected Long requestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDataDto that = (ItemDataDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}