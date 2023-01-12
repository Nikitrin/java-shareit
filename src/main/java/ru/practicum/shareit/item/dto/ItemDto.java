package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.common.ItemCreate;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ItemDto {
    private Integer id;
    @NotBlank(message = "Name can't be blank or null", groups = {ItemCreate.class})
    @Size(min = 1, max = 50, message = "Max length of name is 50 characters, min length is 1 character",
        groups = {ItemCreate.class})
    private String name;
    @NotBlank(message = "Description can't be blank or null", groups = {ItemCreate.class})
    @Size(min = 1, max = 200, message = "Max length of name is 200 characters, min length is 1 character",
        groups = {ItemCreate.class})
    private String description;
    @NotNull(message = "Available can't be null", groups = {ItemCreate.class})
    private Boolean available;
    private User owner;
    private ItemRequest request;
}
