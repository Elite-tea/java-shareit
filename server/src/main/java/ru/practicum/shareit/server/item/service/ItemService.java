package ru.practicum.shareit.server.item.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.server.booking.entity.Booking;
import ru.practicum.shareit.server.booking.repository.BookingRepository;
import ru.practicum.shareit.server.exeption.NotFoundException;
import ru.practicum.shareit.server.exeption.ValidationException;
import ru.practicum.shareit.server.item.controller.ItemController;
import ru.practicum.shareit.server.item.dto.CommentDto;
import ru.practicum.shareit.server.item.dto.ItemDataDto;
import ru.practicum.shareit.server.item.dto.ItemDto;
import ru.practicum.shareit.server.item.entity.Comment;
import ru.practicum.shareit.server.item.entity.Item;
import ru.practicum.shareit.server.item.mapper.CommentMapper;
import ru.practicum.shareit.server.item.mapper.ItemMapper;
import ru.practicum.shareit.server.item.repository.CommentRepository;
import ru.practicum.shareit.server.item.repository.ItemRepository;
import ru.practicum.shareit.server.user.entity.User;
import ru.practicum.shareit.server.user.service.UserService;
import ru.practicum.shareit.server.validation.Validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service("ServerItemService")
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    /**
     * Описание данных методов можно найти в {@link ItemController }
     */
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public Item create(ItemDto itemDto, Long id) {
        Validation.validationItem(itemDto);
        Item item = ItemMapper.toDtoItem(itemDto, userService.getUser(id));
        return itemRepository.save(item);
    }

    public Comment addComment(CommentDto commentDto, Long id, Long itemId) {
        LocalDateTime time = LocalDateTime.now();
        List<Booking> booking = bookingRepository.findByBooker_IdAndItem_Id(id, itemId).stream()
                .filter(b -> b.getEnd().isBefore(time))
                .collect(Collectors.toList());

        if (!booking.isEmpty() && !commentDto.getText().isEmpty()) {
            Comment comment = CommentMapper.commentDtoToComment(commentDto, booking, time);
            return commentRepository.save(comment);
        } else {
            throw new ValidationException(String.format("Предмет с id %d не был арендован или не существует", itemId));
        }
    }

    public Item update(ItemDto itemDto, Long id, Long userId) {
        Item initItem = itemRepository.findById(id).get();
        User user = userService.getUser(userId);
        Item itemUpdate = ItemMapper.dtoItemUpdate(itemDto, initItem, user);
        itemUpdate.setId(id);
        return itemRepository.save(itemUpdate);
    }

    public Collection<ItemDataDto> getItemByUser(Long id, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created"));
        if (from >= 0 && size > 0) {
            try {
                List<Comment> comment = commentRepository.findByItem_Id(id, pageable);
                List<Item> item = itemRepository.findByUserId(id);
                List<ItemDataDto> itemData = new ArrayList<>();
                while (!item.isEmpty()) {
                    itemData.add(ItemMapper.itemToDataDtoNoBooking(item.get(0), commentRepository.findByItem_Id(id, pageable)));
                    item.remove(0);
                }

                LocalDateTime time = LocalDateTime.now();
                List<Booking> booking = bookingRepository.findByItem_User_IdOrderByEndDesc(id).stream()
                        .filter(p -> p.getEnd().isBefore(time)
                                && p.getStart().isBefore(time)
                                && p.getItem().getUser().getId().equals(id))
                        .collect(Collectors.toList());

                List<Booking> bookingNext = bookingRepository.findByItem_User_IdOrderByEndDesc(id).stream()
                        .filter(p -> p.getEnd().isAfter(time)
                                && p.getStart().isAfter(time)
                                && p.getItem().getUser().getId().equals(id))
                        .collect(Collectors.toList());

                List<ItemDataDto> result = new ArrayList<>();
                if (comment == null) {
                    comment = new ArrayList<>();
                }

                if (!booking.isEmpty() && !bookingNext.isEmpty()) {
                    while (!booking.isEmpty() && !bookingNext.isEmpty()) {
                        result.add(ItemMapper.itemToDataDto(booking, bookingNext, comment));
                        booking.remove(0);
                        bookingNext.remove(bookingNext.size() - 1);
                    }
                    result.addAll(itemData);
                    return result.stream()
                            .distinct()
                            .collect(Collectors.toList());
                } else {

                    log.debug("Запрошен предмет c id: {}", id);
                    return itemData;
                }

            } catch (RuntimeException e) {
                log.debug("Предмет не существует");
                throw new NotFoundException(String.format("Предмет с id %d не существует", id));
            }
        } else {
            throw new ValidationException(String.format("Не верно указано количество предметов %d или страниц %d", from, size));
        }
    }

    public ItemDataDto getItemById(Long id, Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "created"));
        if (from >= 0 && size > 0) {
            try {
                List<Comment> comment = commentRepository.findByItem_Id(id, pageable);
                LocalDateTime time = LocalDateTime.now();
                List<Booking> booking = bookingRepository.findByItem_IdOrderByEndDesc(id).stream()
                        .filter(p -> p.getStart().isBefore(time) && p.getItem().getUser().getId().equals(userId))
                        .collect(Collectors.toList());

                List<Booking> bookingNext = bookingRepository.findByItem_IdOrderByEndDesc(id).stream()
                        .filter(p -> p.getEnd().isAfter(time)
                                && p.getStart().isAfter(time)
                                && p.getItem().getUser().getId().equals(userId))
                        .collect(Collectors.toList());

                return ItemMapper.itemToDataDto(booking, bookingNext, comment);
            } catch (RuntimeException e) {
                try {
                    Item item = itemRepository.findById(id).get();
                    log.debug("Запрошен предмет c id: {}", id);
                    return ItemMapper.itemToDataDtoNoBooking(item, commentRepository.findByItem_Id(id, pageable));
                } catch (RuntimeException g) {
                    log.debug("Предмет не существует");
                    throw new NotFoundException(String.format("Предмет с id %d не существует", id));
                }
            }
        } else {
            throw new ValidationException(String.format("Не верно указано количество предметов %d или страниц %d", from, size));
        }
    }

    public Collection<Item> getItemBySearch(String text) {
        if (!text.isBlank()) {
            return itemRepository.findByNameOrDescriptionContainingIgnoreCase(text, text).stream()
                    .filter(Item::getAvailable)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}