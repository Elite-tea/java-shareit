package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    /**
     * Поле сервис
     */
    private final ItemService itemService;

    /**
     * Добавляет вещь в хранилище.
     *
     * @param itemDto объект пользователя.
     * @return возвращает добавленную вещь.
     */
    @PostMapping
    public Item addItem(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemService.getItemStorage().create(itemDto, itemService.getUserStorage().getUserById(id));
    }

    /**
     * Обновляет вещь в хранилище.
     *
     * @param item объект вещи.
     * @return возвращает измененную вещь.
     */
    @PatchMapping("{id}")
    public Item update(@Valid @RequestBody ItemDto item, @PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId) {

        return itemService.getItemStorage().update(item, id, userId);
    }

    /**
     * Запрашивает вещи пользователя.
     *
     * @return возвращает вещи пользователя.
     */
    @GetMapping
    public Collection<Item> getItemByUser(@Valid @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemService.getItemStorage().getItemByUser(id);
    }

    /**
     * Запрашивает вещь пользователя.
     *
     * @return возвращает вещь пользователя.
     */
    @GetMapping("{id}")
    public Item getItemById(@Valid @PathVariable Long id) {
        return itemService.getItemStorage().getItemById(id);
    }

    /**
     * Запрашивает поиск вещи.
     *
     * @return возвращает коллекцию найденных вещей.
     */
    @GetMapping("/search")
    public Collection<Item> getItemBySearch(@RequestParam String text) {
        return itemService.getItemStorage().getItemBySearch(text);
    }
}
