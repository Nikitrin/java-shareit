package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank(message = "Comment can't be blank or null")
    @Size(max = 512, message = "Max length of name is 2048 characters, min length is 1 character")
    private String text;
    private Item item;
    private User author;
    private LocalDateTime created;

    public CommentDto(String text) {
        this.text = text;
    }
}
