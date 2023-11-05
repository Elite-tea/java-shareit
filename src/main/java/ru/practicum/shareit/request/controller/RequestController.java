package ru.practicum.shareit.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemGetRequestDTO;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.entity.Request;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Класс-контроллер для создания и редактирования запросов вещей.
 */
@RestController
@RequestMapping("/requests")
@AllArgsConstructor
public class RequestController {
 private final RequestService requestService;

    /**
     * Добавляет запрос вещи в бд.
     *
     * @param itemRequestDto объект вещи.
     * @param id идентификатор пользователя.
     * @return возвращает добавленную вещь.
     */
    @PostMapping
    public Request create(@Valid @RequestBody ItemRequestDto itemRequestDto,
                          @RequestHeader("X-Sharer-User-Id") Long id) {
        return requestService.create(itemRequestDto, id);
    }

    @GetMapping
    public List<ItemGetRequestDTO> getListRequest(@Valid @RequestHeader("X-Sharer-User-Id") Long id)  {
        return requestService.getListRequest(id);
    }

    @GetMapping("{requestId}")
    public ItemGetRequestDTO getRequest(@Valid @PathVariable Long requestId, @RequestHeader("X-Sharer-User-Id") Long userId)  {
        return requestService.getRequest(requestId, userId);
    }

    @GetMapping("/all")
    public List<ItemGetRequestDTO> getListAllRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                     @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        return requestService.getListAllRequest(userId, from, size);
    }
}
