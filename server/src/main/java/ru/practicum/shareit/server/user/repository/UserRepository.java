package ru.practicum.shareit.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.server.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailContainingIgnoreCase(String emailSearch);
}