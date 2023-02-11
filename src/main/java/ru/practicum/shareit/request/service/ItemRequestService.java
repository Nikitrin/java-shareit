package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long userId);

    List<ItemRequestDto> findRequestsByCurrentUser(Long userId);

    List<ItemRequestDto> findAllItemRequests(Long userId, Integer from, Integer size);

    ItemRequestDto findItemRequestById(Long userId, Long requestId);
}
