package ru.practicum.shareit.server.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.server.booking.entity.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long id, Pageable pageable);

    List<Booking> findByItem_User_IdOrderByStartDesc(Long id, Pageable pageable);

    List<Booking> findByBooker_IdAndItem_Id(Long userId, Long itemId);

    List<Booking> findByItem_User_IdOrderByEndDesc(Long userId);

    List<Booking> findByItem_IdOrderByEndDesc(Long itemId);
}
