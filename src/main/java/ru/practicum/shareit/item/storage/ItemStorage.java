package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {

    Item createItem(Item item);

    Item updateItem(Item item);

    Item getItemById(Integer itemId, Integer userId);

    List<Item> getAllItems(Integer userId);

    List<Item> searchItemsByText(String text);
}
