package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.common.BookingMarker;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoSuccess;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private BookingService bookingService;

    @PostMapping
    public BookingDtoSuccess createBooking(
        @Validated(BookingMarker.onCreate.class)
        @RequestBody BookingDto bookingDto,
        @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Create booking: start = {}, end = {}, item id = {}, user id = {}",
            bookingDto.getStart(), bookingDto.getEnd(), bookingDto.getItemId(), userId);
        return bookingService.createBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoSuccess updateBooking(
        @RequestHeader("X-Sharer-User-Id") Long userId,
        @PathVariable Long bookingId,
        @RequestParam Boolean approved) {
        log.info("Update booking with id = {}, owner with user id = {}, approved is = {}",
            bookingId, userId, approved);
        return bookingService.updateBooking(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoSuccess getBooking(
        @RequestHeader("X-Sharer-User-Id") Long userId,
        @PathVariable Long bookingId) {
        log.info("Get booking with id = {}, user id = {}", bookingId, userId);
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDtoSuccess> getBookingCurrentUser(
        @RequestHeader("X-Sharer-User-Id") Long userId,
        @RequestParam(defaultValue = "ALL", required = false) String state ) {
        log.info("Get booking current user id = {}, state = {}", userId, state);
        return bookingService.getBookingCurrentUser(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoSuccess> getBookingOwner(
        @RequestHeader("X-Sharer-User-Id") Long userId,
        @RequestParam(defaultValue = "ALL", required = false) String state ) {
        log.info("Get booking owner id = {}, state = {}", userId, state);
        return bookingService.getBookingOwner(userId, state);
    }
}
