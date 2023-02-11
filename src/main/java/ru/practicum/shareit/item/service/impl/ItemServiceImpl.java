package ru.practicum.shareit.item.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingShort;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.handler.exception.ForbiddenException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.CommentShort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private CommentRepository commentRepository;
    private ItemRequestRepository itemRequestRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        User owner = userRepository.findById(userId).orElseThrow();
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(owner);
        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemDto.getRequestId()).orElseThrow();
            item.setRequest(itemRequest);
        }
        return ItemMapper.toDto(itemRepository.save(item), null, null, null);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        User owner = userRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        if (!item.getOwner().equals(owner)) throw new ForbiddenException("The item was added by another user");
        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setIsAvailable(itemDto.getAvailable());
        List<CommentShort> comments = commentRepository.getAllByItem(itemId).stream()
            .map(CommentMapper::toCommentShort)
            .collect(Collectors.toList());
        return ItemMapper.toDto(itemRepository.save(item),
            getLastBooking(itemId),
            getNextBooking(itemId),
            comments);
    }

    @Override
    public ItemDto getItemById(Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        List<CommentShort> comments = commentRepository.getAllByItem(itemId).stream()
            .map(CommentMapper::toCommentShort)
            .collect(Collectors.toList());
        if (item.getOwner().getId().equals(userId)) {
            return ItemMapper.toDto(itemRepository.save(item),
                getLastBooking(itemId),
                getNextBooking(itemId),
                comments);
        } else {
            return ItemMapper.toDto(item,
                null,
                null,
                comments);
        }
    }

    @Override
    public List<ItemDto> getAllItems(Long userId) {
        List<Item> items = itemRepository.getUsersItem(userId);
        List<ItemDto> itemDto = new ArrayList<>();
        for (Item item : items) {
            itemDto.add(ItemMapper.toDto(item,
                getLastBooking(item.getId()),
                getNextBooking(item.getId()), null));
        }
        return itemDto;
    }

    @Override
    public List<ItemDto> searchItemsByText(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return new ArrayList<>();
        }
        text = text.toLowerCase();
        List<Item> items = itemRepository.searchItemsByText(text);
        List<ItemDto> itemDto = new ArrayList<>();
        for (Item item : items) {
            itemDto.add(ItemMapper.toDto(item, null, null, null));
        }
        return itemDto;
    }

    private BookingShort getLastBooking(Long itemId) {
        List<Booking> bookings = bookingRepository.getLastBooking(itemId);
        if (bookings.isEmpty()) return null;
        return new BookingShort(bookings.get(0).getId(),
            bookings.get(0).getBooker().getId(),
            bookings.get(0).getStatus());
    }

    private BookingShort getNextBooking(Long itemId) {
        List<Booking> bookings = bookingRepository.getNextBooking(itemId);
        if (bookings.isEmpty()) return null;
        return new BookingShort(bookings.get(0).getId(),
            bookings.get(0).getBooker().getId(),
            bookings.get(0).getStatus());
    }
}
