package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select item " +
        "from Item item " +
        "where item.owner.id = ?1 " +
        "order by item.id")
    List<Item> getUsersItem(Long ownerId);

    @Query("select item " +
        "from Item item " +
        "where item.isAvailable = true and " +
        "(lower(item.name) like concat('%', ?1, '%') or " +
        "lower(item.description) like concat('%', ?1, '%')) " +
        "order by item.id")
    List<Item> searchItemsByText(String text);

    @Query("select item " +
        "from Item item " +
        "where item.request.id = ?1 " +
        "order by item.request.id desc")
    Set<Item> findItemByRequestId(Long requestId);
}
