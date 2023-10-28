package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.entity.Booking;

import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(long userId);

    Collection<Booking> findByItem_User_IdOrderByStartDesc(Long userId);

    List<Booking> findByBooker_IdAndItem_Id(Long userId, Long itemId);

    List<Booking> findByItem_User_IdOrderByEndDesc(Long userId);

    List<Booking> findByItem_IdOrderByEndDesc(Long itemId);
}
