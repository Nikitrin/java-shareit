package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentShort;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
            comment.getId(),
            comment.getText(),
            comment.getItem(),
            comment.getAuthor(),
            comment.getCreated());
    }

    public static Comment toComment(CommentDto commentDto) {
        return new Comment(
            commentDto.getId(),
            commentDto.getText(),
            commentDto.getItem(),
            commentDto.getAuthor(),
            commentDto.getCreated());
    }

    public static CommentShort toCommentShort(Comment comment) {
        return new CommentShort(
            comment.getId(),
            comment.getText(),
            comment.getAuthor().getName(),
            comment.getCreated());
    }
}
