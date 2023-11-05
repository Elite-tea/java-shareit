package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDataDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

/**
 * Класс-контроллер для создания и редактирования вещей.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    /**
     * Добавляет вещь в бд.
     *
     * @param itemDto объект вещи.
     * @param id идентификатор пользователя.
     * @return возвращает добавленную вещь.
     */
    @PostMapping
    public Item create(@Valid @RequestBody ItemDto itemDto,
                        @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemService.create(itemDto, id);
    }

    /**
     * Обновляет вещь в бд.
     *
     * @param item объект вещи.
     * @param id идентификатор вещи.
     * @param userId идентификатор пользователя.
     * @return возвращает измененную вещь.
     */
    @PatchMapping("{id}")
    public Item update(@Valid @RequestBody ItemDto item,
                       @PathVariable Long id,
                       @RequestHeader("X-Sharer-User-Id") Long userId) {

        return itemService.update(item, id, userId);
    }

    /**
     * Добавляет комментарий к вещи, пользователем, который брал ее в аренду.
     *
     * @param commentDto сущность комментария.
     * @param id идентификатор пользователя комментария.
     * @param itemId идентификатор вещи.
     * @return возвращает добавленный комментарий.
     */
    @PostMapping("/{itemId}/comment")
    public Comment addComment(@Valid @RequestBody CommentDto commentDto,
                              @RequestHeader("X-Sharer-User-Id") Long id,
                              @PathVariable Long itemId) {
        return itemService.addComment(commentDto, id, itemId);
    }

    /**
     * Запрашивает вещи пользователя.
     *
     * @return возвращает коллекцию вещей пользователя.
     */
    @GetMapping
    public Collection<ItemDataDto> getItemByUser(@Valid @RequestHeader("X-Sharer-User-Id") Long id,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        return itemService.getItemByUser(id, from, size);
    }

    /**
     * Запрашивает вещь пользователя по идентификатору
     *
     * @param id идентификатор вещи.
     * @param userId идентификатор пользователя.
     * @return возвращает вещь пользователя.
     */
    @GetMapping("{id}")
    public ItemDataDto getItemById(@Valid @PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId,
                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        return itemService.getItemById(id, userId, from, size);
    }

    /**
     * Запрашивает поиск вещи.
     *
     * @param text текст для поиска вещей.
     * @return возвращает коллекцию найденных вещей.
     */
    @GetMapping("/search")
    public Collection<Item> getItemBySearch(@RequestParam String text) {
        return itemService.getItemBySearch(text);
    }
}
