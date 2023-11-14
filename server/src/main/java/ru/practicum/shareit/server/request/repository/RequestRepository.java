package ru.practicum.shareit.server.request.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.server.request.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByRequestor_IdOrderByCreatedDesc(Long id);

    List<Request> findAllByRequestorIdNot(Long userId, Pageable pageable);
}
