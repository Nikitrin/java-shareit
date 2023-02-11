package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoSuccess;

import java.util.List;

public interface BookingService {

    BookingDtoSuccess createBooking(BookingDto bookingDto, Long userId);

    BookingDtoSuccess updateBooking(Long bookingId, Long userId, Boolean approved);

    BookingDtoSuccess getBooking(Long bookingId, Long userId);

    List<BookingDtoSuccess> getBookingCurrentUser(Long userId, String state);

    List<BookingDtoSuccess> getBookingOwner(Long userId, String state);
}
