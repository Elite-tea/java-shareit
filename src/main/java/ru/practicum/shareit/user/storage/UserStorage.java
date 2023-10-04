package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Map;

/**
 * Интерфейс для работы с хранилищем пользователей, реализован в {@link  } и {@link  }
 */
public interface UserStorage {
    /**
     * Метод добавления пользователя
     *
     * @param user объект пользователя
     * @return возвращает созданного пользователя
     */
    User create(User user);

    /**
     * Метод обновления пользователя
     *
     * @param user объект пользователя
     * @return возвращает обновленного пользователя
     */
    User update(User user, Long id);

    /**
     * Метод удаления пользователя по идентификатору.
     *
     * @param id идентификатор удаляемого пользователя
     */
    void deleteUser(Long id);

    /**
     * Метод запроса пользователей
     *
     * @return возвращает коллекцию пользователей
     */
    Collection<User> getUser();

    /**
     * Метод запроса пользователя по id
     *
     * @param id идентификатор пользователя
     * @return возвращает пользователя по id
     */
    User getUserById(Long id);

    Map<Long, User> getUsers();
}
