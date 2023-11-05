package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exeption.AlreadyExistsException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    /**
     * Описание данных методов можно найти в {@link ru.practicum.shareit.booking.controller.BookingController }
     *
     */
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;

    public Booking create(BookingDto bookingDto, Long id) {
        Item item;
        LocalDateTime time = LocalDateTime.now();

        try {
            item = itemRepository.findById(bookingDto.getItemId()).get();
        } catch (RuntimeException g) {
            log.debug("Предмет не существует");
            throw new NotFoundException(String.format("Предмет с id %d не существует", id));
        }

        Booking booking = BookingMapper.toDtoNewBooking(bookingDto, userService.getUser(id), item, time);
        if (booking.getStart().equals(time)
                || booking.getEnd().equals(time)
                || !item.getAvailable()
                || booking.getEnd().isBefore(booking.getStart())
                || booking.getStart().equals(booking.getEnd())
                || booking.getStart().isBefore(time)
        ) {
            throw new ValidationException(String.format("Предмет %d недоступен", item.getId()));
        } else if (item.getUser().getId().equals(id)) {
            throw new NotFoundException("Невозможно арендовать у самого себя");
        } else {
            return bookingRepository.save(booking);
        }
    }

    public Collection<Booking> getAllBookingByUser(Long id, BookingStatus state, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "start"));
        if (from >= 0 && size > 0) {
        LocalDateTime time = LocalDateTime.now();
        Collection<Booking> collectionBooking = bookingRepository.findByBookerIdOrderByStartDesc(id, pageable);
                if (collectionBooking.isEmpty()) {
                    throw new AlreadyExistsException("Ошибка доступа к получению данных, пользователь не существует");
                } else {
                    return getAllBookingByBookerId(state, collectionBooking, time);
                }
        } else {
            throw new ValidationException(String.format("Не верно указано количество предметов %d или страниц %d", from, size));
        }
            }

    public Collection<Booking> getAllBookingItemByUser(Long id, BookingStatus state, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "start"));
            if (from >= 0 && size > 0) {
        LocalDateTime time = LocalDateTime.now();
        Collection<Booking> collectionBooking = bookingRepository.findByItem_User_IdOrderByStartDesc(id, pageable);
                if (collectionBooking.isEmpty()) {
                    throw new AlreadyExistsException("Ошибка доступа к получению данных, пользователь не существует");
                } else {
                    return getAllBookingByBookerId(state, collectionBooking, time);
                }
            } else {
                throw new ValidationException(String.format("Не верно указано количество предметов %d или страниц %d", from, size));
            }
            }


    public Booking getBookingByUser(Long bookingId, Long userId) {
        try {
            Booking booking = bookingRepository.findById(bookingId).get();
            if (booking.getBooker().getId().equals(userId) || booking.getItem().getUser().getId().equals(userId)) {
                return booking;
            } else {
                throw new ValidationException("Ошибка доступа к получению данных");
            }
        } catch (RuntimeException e) {
            throw new NotFoundException("Запрос не существует");
        }
    }

    public Booking update(Long bookingId, Long userId, Boolean approved) {
        Booking bookingUpdate = bookingRepository.findById(bookingId).get();
        Long userBookingOwner = bookingUpdate.getItem().getUser().getId();
        if (userBookingOwner.equals(userId) && bookingUpdate.getStatus() == BookingStatus.WAITING) {
            if (approved) {
                bookingUpdate.setStatus(BookingStatus.APPROVED);
            } else {
                bookingUpdate.setStatus(BookingStatus.REJECTED);
            }
            return bookingRepository.save(bookingUpdate);
        } else if (bookingUpdate.getStatus() != (BookingStatus.WAITING)) {
            throw new ValidationException(String.format("Обновление запроса %d невозможно, запрос обработан", bookingId));
        } else {
            throw new NotFoundException(String.format("Обновление запроса %d невозможно, нет доступа.", bookingId));
        }
    }

    private Collection<Booking> getAllBookingByBookerId(BookingStatus state, Collection<Booking> booking, LocalDateTime time) {
        switch (state) {
            case ALL :
                return booking;
            case CURRENT :
                return booking.stream()
                        .filter(p -> time.isAfter(p.getStart()) && time.isBefore(p.getEnd()))
                        .collect(Collectors.toList());
            case PAST :
                return booking.stream()
                        .filter(p -> time.isAfter(p.getEnd()))
                        .collect(Collectors.toList());
            case FUTURE :
                return booking.stream()
                        .filter(p -> time.isBefore(p.getStart()))
                        .collect(Collectors.toList());
            case WAITING :
                return booking.stream()
                        .filter(p -> p.getStatus() == BookingStatus.WAITING)
                        .collect(Collectors.toList());
            case REJECTED :
                return booking.stream()
                        .filter(p -> p.getStatus() == BookingStatus.REJECTED)
                        .collect(Collectors.toList());
            default:
                throw new ValidationException(String.format("Unknown state: %s", state));
        }
    }
}