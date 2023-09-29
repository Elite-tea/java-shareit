package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validation.Validation;

import java.util.*;
import java.util.stream.Collectors;

@Component()
@Slf4j
public class InMemoryItemStorage implements ItemStorage {
    /**
     * Поле хранилище пользователей
     */
    private final HashMap<Long, Item> items = new HashMap<>();

    /**
     * Поле счетчик идентификаторов пользователей
     */
    private Long id = 1L;

    @Override
    public Item create(ItemDto itemDto, User user) {
        Validation.validationItem(itemDto, user);
        itemDto.setId(id);
        Item item = ItemMapper.toDtoItem(itemDto, user);
        log.debug("Предмет добавлен");
        items.put(id, item);
        id++;
        return item;
    }

    @Override
    public Item update(ItemDto item, Long id, Long userId) {
        Item initItem = items.get(id);
        Item updateItem;
        Validation.validationUpdateItem(userId, initItem);
        if (items.containsKey(id)) {
            updateItem = ItemMapper.toDtoItemUpdate(item,initItem);
            items.put(id,updateItem);
        } else {
            log.debug("Предмета не существует");
            throw new NotFoundException(String.format("Предмета с id %d не существует", id));
        }
        return updateItem;
    }

    @Override
    public Item getItemById(Long id) {
        if (items.containsKey(id)) {
            log.debug("Запрошен предмет c id: {}", id);
            return items.get(id);
        } else {
            log.debug("Предмет не существует");
            throw new NotFoundException(String.format("Предмет с id %d не существует", id));
        }
    }

    @Override
    public Collection<Item> getItemByUser(Long id) {
        log.debug("Запрошен предмет пользователя с id: {}", id);
        return items.entrySet().stream()
                .filter(item -> item.getValue().getOwner().getId().equals(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }

    /**
     * Метод получение вещи по поиску.
     *
     * @param text текст поиска
     * @return возвращает коллекцию предметов содержащие слова из поиска.
     */

    @Override
    public Collection<Item> getItemBySearch(String text) {
        log.debug("Запрошен предмет пользователя с id: {}", id);
        List<String> words = Arrays.asList(text.toLowerCase().split(""));
        if (text.isBlank()) {
            return new ArrayList<>();
        } else {
            Collection<Item> completedItems = new ArrayList<>();
            for (long i = 1L; i <= items.size(); i++) {
                List<String> word = Arrays.asList(items.get(i).getDescription().split(""));
                List<String> wordsName = Arrays.asList(items.get(i).getName().split(" "));
                List<String> wordName = Arrays.asList(items.get(i).getName().split(""));

                for (int j = 1; j <= items.size(); j++) {
                    if (!completedItems.contains(items.get(i)) &&
                            items.get(i).getAvailable() &&
                            (word.get((int) i).equals(words.get(j)) ||
                             wordName.get((int) i).equals(words.get(j)))) {
                        completedItems.add(items.get(i));
                    } else if (!completedItems.contains(items.get(i)) &&
                            items.get(i).getAvailable() && wordsName.get(j - 1).equals(text.toLowerCase())) {
                        completedItems.add(items.get(i));
                    }
                }
            }
            return completedItems;
        }
    }
}