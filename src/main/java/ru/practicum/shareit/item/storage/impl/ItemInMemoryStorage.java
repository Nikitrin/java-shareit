package ru.practicum.shareit.item.storage.impl;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.handler.exception.ForbiddenException;
import ru.practicum.shareit.handler.exception.NotFoundException;
import ru.practicum.shareit.handler.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemInMemoryStorage implements ItemStorage {
    private final Map<Integer, Item> items = new HashMap<>();
    private Integer id = 1;

    @Override
    public Item createItem(Item item) {
        item.setId(id);
        items.put(id, item);
        id++;
        return item;
    }

    @Override
    public Item updateItem(Item updateItem) {
        if (updateItem.getId() != null && !items.containsKey(updateItem.getId())) {
            throw new NotFoundException(String.format("Item with id = %s not found", updateItem.getId()));
        }
        Item item = items.get(updateItem.getId());
        if (!updateItem.getOwner().equals(item.getOwner())) {
            throw new ForbiddenException(
                    String.format("You cannot update item with id = %s. Item created another user", item.getId()));
        }
        if (updateItem.getName() != null) {
            item.setName(updateItem.getName());
        }
        if (updateItem.getDescription() != null) {
            item.setDescription(updateItem.getDescription());
        }
        if (updateItem.getAvailable() != null) {
            item.setAvailable(updateItem.getAvailable());
        }
        if (updateItem.getRequest() != null) {
            item.setRequest(updateItem.getRequest());
        }
        return item;
    }

    @Override
    public Item getItemById(Integer itemId, Integer userId) {
        if (!items.containsKey(itemId)) {
            throw new NotFoundException(String.format("Item with id = %s not found", itemId));
        }
        return items.get(itemId);
    }

    @Override
    public List<Item> getAllItems(Integer userId) {
        return new ArrayList<>(items.values()).stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> searchItemsByText(String text) {
        return new ArrayList<>(items.values()).stream()
                .filter(item -> item.getAvailable().equals(true))
                .filter(item -> (
                        item.getName().toLowerCase().contains(text) ||
                                item.getDescription().toLowerCase().contains(text)))
                .collect(Collectors.toList());
    }
}
