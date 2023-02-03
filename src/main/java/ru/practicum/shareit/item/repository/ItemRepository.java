package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select item " +
        "from Item item " +
        "where item.owner.id = ?1")
    List<Item> getUsersItem(Long ownerId);

    @Query("select item " +
        "from Item item " +
        "where item.isAvailable = true and " +
        "(lower(item.name) like concat('%', ?1, '%') or " +
        "lower(item.description) like concat('%', ?1, '%'))")
    List<Item> searchItemsByText(String text);
}
