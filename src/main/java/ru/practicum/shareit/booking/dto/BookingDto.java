package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.common.BookingMarker;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    @FutureOrPresent(message = "The start of the booking cannot be in the past",
        groups = {BookingMarker.OnCreate.class})
    @NotNull(message = "Start date can't be null", groups = {BookingMarker.OnCreate.class})
    private LocalDateTime start;
    @Future(message = "The end of the booking cannot be in the past",
        groups = {BookingMarker.OnCreate.class})
    @NotNull(message = "End date can't be null", groups = {BookingMarker.OnCreate.class})
    private LocalDateTime end;
    private Long itemId;
    private Long bookerId;
    private String status;
}
