package ru.practicum.shareit.gateway.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.gateway.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
   private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                         @RequestHeader("X-Sharer-User-Id") Long id) {
        return requestClient.create(itemRequestDto, id);
    }

    @GetMapping
    public ResponseEntity<Object> getListRequest(@Valid @RequestHeader("X-Sharer-User-Id") Long id) {
        return requestClient.getListRequest(id);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@Valid @PathVariable Long requestId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.getRequest(requestId, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getListAllRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(defaultValue = "10") Integer size) {
        return requestClient.getListAllRequest(userId, from, size);
    }
}
