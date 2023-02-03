package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.common.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoSuccess {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;
}