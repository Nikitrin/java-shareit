package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.model.BookingShort;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.CommentShort;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemMapper {
    public static ItemDto toDto(Item item, BookingShort lastBooking, BookingShort nextBooking,
                                List<CommentShort> comments) {
        return new ItemDto(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getIsAvailable(),
            item.getOwner(),
            item.getRequestId(),
            lastBooking,
            nextBooking,
            comments);
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
