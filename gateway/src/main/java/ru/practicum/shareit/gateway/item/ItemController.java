package ru.practicum.shareit.gateway.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.item.dto.CommentDto;
import ru.practicum.shareit.gateway.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto,
                                         @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemClient.create(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ItemDto item,
                       @PathVariable Long id,
                       @RequestHeader("X-Sharer-User-Id") Long userId) {

        return itemClient.update(item, id, userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@Valid @RequestBody CommentDto commentDto,
                              @RequestHeader("X-Sharer-User-Id") Long id,
                              @PathVariable Long itemId) {
        return itemClient.addComment(commentDto, id, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemByUser(@Valid @RequestHeader("X-Sharer-User-Id") Long id,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Long from,
                                                 @Positive @RequestParam(defaultValue = "10") Long size) {
        return itemClient.getItemByUser(id, from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@Valid @PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId,
                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        return itemClient.getItemById(id, userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemBySearch(@RequestParam String text) {
        return itemClient.getItemBySearch(text);
    }
}
