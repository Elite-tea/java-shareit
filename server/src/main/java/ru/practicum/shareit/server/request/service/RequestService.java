package ru.practicum.shareit.server.request.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.server.exeption.NotFoundException;
import ru.practicum.shareit.server.exeption.ValidationException;
import ru.practicum.shareit.server.item.service.ItemService;
import ru.practicum.shareit.server.request.controller.RequestController;
import ru.practicum.shareit.server.request.dto.ItemGetRequestDTO;
import ru.practicum.shareit.server.request.dto.ItemRequestDto;
import ru.practicum.shareit.server.request.entity.Request;
import ru.practicum.shareit.server.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.server.request.repository.RequestRepository;
import ru.practicum.shareit.server.user.entity.User;
import ru.practicum.shareit.server.user.service.UserService;
import ru.practicum.shareit.server.validation.Validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Service("ServerRequestService")
@RequiredArgsConstructor
@Slf4j
public class RequestService {
    /**
     * Описание данных методов можно найти в {@link RequestController }
     */
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final ItemService itemService;

    public Request create(ItemRequestDto itemRequestDto, Long id) {
        try {
            LocalDateTime time = LocalDateTime.now();
            Request itemRequest = ItemRequestMapper.dtoToItemRequest(itemRequestDto, userService.getUser(id), time);
            return requestRepository.save(itemRequest);
        } catch (NullPointerException e) {
            throw new ValidationException("Запрос пуст");
        }
    }

    public List<ItemGetRequestDTO> getListRequest(Long id) {
        User user = userService.getUser(id);
        Validation.checkValidUserGetRequest(user);
        List<ItemGetRequestDTO> requestsResult = new ArrayList<>();

        requestRepository.findByRequestor_IdOrderByCreatedDesc(id)
                .forEach(i -> requestsResult.add(ItemRequestMapper.transformationGetRequestDTO(
                        i, itemService.getItemRepository().findByRequestId(i.getId()))));

        return requestsResult;
    }

    public ItemGetRequestDTO getRequest(Long requestId, Long userId) {
        User user = userService.getUser(userId);
        Validation.checkValidUserGetRequest(user);
        try {
            return ItemRequestMapper.transformationGetRequestDTO(requestRepository.findById(requestId).get(),
                    itemService.getItemRepository().findByRequestId(requestId));
        } catch (NoSuchElementException e) {
            throw new NotFoundException(String.format("Запрос %d не существует", requestId));
        }
    }

    public List<ItemGetRequestDTO> getListAllRequest(Long userId, Integer from, Integer size) {
        User user = userService.getUser(userId);
        Validation.checkValidUserGetRequest(user);

        Pageable pageable = PageRequest.of(from / size, size,
                Sort.by(Sort.Direction.DESC, "created"));

        List<ItemGetRequestDTO> requestsResult = new ArrayList<>();
        if (from >= 0 && size > 0) {
            requestRepository.findAllByRequestorIdNot(userId, pageable)
                    .forEach(i -> requestsResult.add(ItemRequestMapper.transformationGetRequestDTO(
                            i, itemService.getItemRepository().findByRequestId(i.getId()))));
            return requestsResult;
        } else {
            throw new ValidationException(String.format("Не верно указано количество предметов %d или страниц %d", from, size));
        }

    }
}