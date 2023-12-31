package ru.practicum.shareit.gateway.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.gateway.client.BaseClient;
import ru.practicum.shareit.gateway.item.dto.CommentDto;
import ru.practicum.shareit.gateway.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(ru.practicum.shareit.gateway.item.dto.ItemDto itemDto, Long id) {
        return post("", id, itemDto);
    }

    public ResponseEntity<Object> addComment(CommentDto commentDto, Long id, Long itemId) {
        return post("/" + itemId + "/comment", id, commentDto);
    }

    public ResponseEntity<Object> update(ItemDto item, Long id, Long userId) {
        return patch("/" + id, userId, item);
    }

    public ResponseEntity<Object> getItemByUser(Long id, Long from, Long size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/", id, parameters);
    }

    public ResponseEntity<Object> getItemById(Long id, Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/" + id, userId, parameters);
    }

    public ResponseEntity<Object> getItemBySearch(String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search?text={text}",0L,parameters);
    }

}
