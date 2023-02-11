package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.BookingShort;
import ru.practicum.shareit.item.model.CommentShort;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    @NotBlank(message = "Name can't be blank or null")
    @Size(min = 1, max = 50, message = "Max length of name is 50 characters, min length is 1 character")
    private String name;
    @NotBlank(message = "Description can't be blank or null")
    @Size(min = 1, max = 200, message = "Max length of name is 200 characters, min length is 1 character")
    private String description;
    @NotNull(message = "Available can't be null")
    private Boolean available;
    private User owner;
    private Long requestId;
    private BookingShort lastBooking;
    private BookingShort nextBooking;
    private List<CommentShort> comments;
}