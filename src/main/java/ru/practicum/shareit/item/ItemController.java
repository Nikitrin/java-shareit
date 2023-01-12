package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.common.ItemCreate;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    @PostMapping
    public ItemDto createItem(
        @Validated(ItemCreate.class) @RequestBody ItemDto itemDto,
        @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("UserCreate item: name = {}, owner with user id = {}", itemDto.getName(), userId);
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(
            @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Integer userId,
            @PathVariable Integer itemId) {
        log.info("UserUpdate item with id = {}, owner with user id = {}", itemId, userId);
        return itemService.updateItem(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(
            @PathVariable Integer itemId,
            @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Get item with id = {}, owner with user id = {}", itemId, userId);
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Get all items user id = {}", userId);
        return itemService.getAllItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItemsByText(@RequestParam String text) {
        log.info("Search items by text = {}", text);
        return itemService.searchItemsByText(text);
    }
}
