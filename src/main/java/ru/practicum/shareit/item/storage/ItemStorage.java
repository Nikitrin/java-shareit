package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage extends JpaRepository<Item, Long> {

    Item createItem(Item item);

    Item updateItem(Item item);

    Item getItemById(Integer itemId, Integer userId);

    List<Item> getAllItems(Integer userId);

    List<Item> searchItemsByText(String text);
}
