package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoSuccess;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(
            booking.getId(),
            booking.getStart(),
            booking.getEnd(),
            booking.getItemId(),
            booking.getBooker(),
            booking.getStatus());
    }

    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(
            bookingDto.getId(),
            bookingDto.getStart(),
            bookingDto.getEnd(),
            bookingDto.getItemId(),
            bookingDto.getBooker(),
            bookingDto.getStatus());
    }

    public static BookingDtoSuccess toDtoSuccess(Booking booking, Item item, User user) {
        return new BookingDtoSuccess(
            booking.getId(),
            booking.getStart(),
            booking.getEnd(),
            item,
            user,
            booking.getStatus());
    }
}