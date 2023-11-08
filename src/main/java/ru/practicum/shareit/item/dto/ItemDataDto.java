package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDataDto;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.user.entity.User;

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
    protected List<Comment> comments;
    protected User user;
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