package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Integer userId);

    ItemDto updateItem(ItemDto itemDto, Integer itemId, Integer userId);

    ItemDto getItemById(Integer itemId, Integer userId);

    List<ItemDto> getAllItems(Integer userId);

    List<ItemDto> searchItemsByText(String text);
}
