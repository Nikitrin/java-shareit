package ru.practicum.shareit.request.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.handler.exception.AvailableException;
import ru.practicum.shareit.item.dto.ItemShort;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private ItemRequestRepository itemRequestRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;

    @Override
    public ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> findRequestsByCurrentUser(Long userId) {
        userRepository.findById(userId).orElseThrow();
        List<ItemRequest> itemRequests = itemRequestRepository.findItemsByRequesterId(userId);
        List<ItemRequestDto> listDto = new ArrayList<>();
        for (ItemRequest itemRequest : itemRequests) {
            ItemRequestDto dto = ItemRequestMapper.toDto(itemRequest);
            Set<Item> items = itemRepository.findItemByRequestId(itemRequest.getId());
            Set<ItemShort> itemShorts = items.stream().map(ItemMapper::toItemShort).collect(Collectors.toSet());
            dto.setItems(itemShorts);
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public List<ItemRequestDto> findAllItemRequests(Long userId, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow();
        if (from < 0 || size <= 0) throw new AvailableException("From and size cannot be less than 0");
        List<ItemRequest> itemRequests = itemRequestRepository.findAllItemRequests(
            userId, PageRequest.of(from, size));
        for (ItemRequest itemRequest : itemRequests) {
            Set<ItemShort> items = itemRepository.findItemByRequestId(itemRequest.getId()).stream()
                .map(ItemMapper::toItemShort)
                .collect(Collectors.toSet());
            itemRequest.setItems(items);
        }
        return itemRequests.stream().map(ItemRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto findItemRequestById(Long userId, Long requestId) {
        userRepository.findById(userId).orElseThrow();
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow();
        Set<ItemShort> items = itemRepository.findItemByRequestId(itemRequest.getId()).stream()
            .map(ItemMapper::toItemShort)
            .collect(Collectors.toSet());
        itemRequest.setItems(items);
        return ItemRequestMapper.toDto(itemRequest);
    }
}
