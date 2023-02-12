package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.model.BookingShort;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShort;
import ru.practicum.shareit.item.model.CommentShort;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemMapper {
    public static ItemDto toDto(Item item, BookingShort lastBooking, BookingShort nextBooking,
                                List<CommentShort> comments) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getIsAvailable());
        itemDto.setOwner(item.getOwner());
        if (item.getRequest() != null) itemDto.setRequestId(item.getRequest().getId());
        itemDto.setLastBooking(lastBooking);
        itemDto.setNextBooking(nextBooking);
        itemDto.setComments(comments);
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setIsAvailable(itemDto.getAvailable());
        item.setOwner(itemDto.getOwner());
        return item;
    }

    public static ItemShort toItemShort(Item item) {
        ItemShort itemShort = new ItemShort();
        itemShort.setId(item.getId());
        itemShort.setName(item.getName());
        itemShort.setOwnerId(item.getOwner().getId());
        itemShort.setDescription(item.getDescription());
        itemShort.setRequestId(item.getRequest().getId());
        itemShort.setAvailable(item.getIsAvailable());
        return itemShort;
    }
}
