package ru.practicum.shareit.item.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImplItemService implements ItemService {
    private ItemStorage itemStorage;
    private UserStorage userStorage;

    @Override
    public ItemDto createItem(ItemDto itemDto, Integer userId) {
        User user = userStorage.getUserById(userId);
        Item item = ItemDto.toItem(itemDto);
        item.setOwner(user);
        return ItemDto.toItemDto(itemStorage.createItem(item));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Integer itemId, Integer userId) {
        User user = userStorage.getUserById(userId);
        Item item = ItemDto.toItem(itemDto);
        item.setOwner(user);
        item.setId(itemId);
        return ItemDto.toItemDto(itemStorage.updateItem(item));
    }

    @Override
    public ItemDto getItemById(Integer itemId, Integer userId) {
        return ItemDto.toItemDto(itemStorage.getItemById(itemId, userId));
    }

    @Override
    public List<ItemDto> getAllItems(Integer userId) {
        List<Item> items = itemStorage.getAllItems(userId);
        return items.stream().map(ItemDto::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItemsByText(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return new ArrayList<>();
        }
        text = text.toLowerCase();
        List<Item> items = itemStorage.searchItemsByText(text);
        return items.stream().map(ItemDto::toItemDto).collect(Collectors.toList());
    }
}
