package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toDto(Item item) {
        return new ItemDto(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getIsAvailable(),
            item.getOwner(),
            item.getRequestId());
    }

    public static Item toItem(ItemDto itemDto) {
        return new Item(
            itemDto.getId(),
            itemDto.getName(),
            itemDto.getDescription(),
            itemDto.getAvailable(),
            itemDto.getOwner(),
            itemDto.getRequestId());
    }
}
