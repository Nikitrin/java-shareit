package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.CommentShort;

public interface CommentService {
    CommentShort createComment(CommentDto commentDto, Long userId, Long itemId);
}
