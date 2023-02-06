package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoSuccess;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(
            booking.getId(),
            booking.getStart(),
            booking.getEnd(),
            booking.getItem().getId(),
            booking.getBooker().getId(),
            booking.getStatus());
    }

    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(
            bookingDto.getId(),
            bookingDto.getStart(),
            bookingDto.getEnd(),
            null,
            null,
            bookingDto.getStatus());
    }

    public static BookingDtoSuccess toDtoOut(Booking booking) {
        return new BookingDtoSuccess(
            booking.getId(),
            booking.getStart(),
            booking.getEnd(),
            booking.getItem(),
            booking.getBooker(),
            booking.getStatus());
    }
}
