package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;


public class CommentMapper {
    /**
     * Метод преобразовывает данные в сущность <b>Comment</b>
     *
     * @param commentDto объект комментария
     * @param booking    список аренд для вещи
     * @param time       время создания комментария
     */
    public static Comment commentDtoToComment(CommentDto commentDto, List<Booking> booking, LocalDateTime time) {
        return new Comment(commentDto.getId(),
                commentDto.getText(),
                booking.get(0).getItem(),
                booking.get(0).getBooker().getName(),
                time);
    }
}
