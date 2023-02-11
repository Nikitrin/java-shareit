package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto createItemRequest(
        @RequestHeader("X-Sharer-User-Id") Long userId,
        @Validated @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Create ItemRequest: description = {}, requester user id = {}",
            itemRequestDto.getDescription(), userId);
        return itemRequestService.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> findItemRequestsByCurrentUser(
        @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Find item requests by current user id = {}", userId);
        return itemRequestService.findRequestsByCurrentUser(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> findAllItemRequests(
        @RequestHeader("X-Sharer-User-Id") Long userId,
        @RequestParam(defaultValue = "0", required = false) Integer from,
        @RequestParam(defaultValue = "20", required = false) Integer size) {
        log.info("User id = {} find all item requests from = {} and size = {}", userId, from, size);
        return itemRequestService.findAllItemRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto findItemRequestById(
        @PathVariable Long requestId,
        @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("User id = {} find item requests id = {}", userId, requestId);
        return itemRequestService.findItemRequestById(userId, requestId);
    }
}
