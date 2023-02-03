package ru.practicum.shareit.booking.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.common.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoSuccess;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.handler.exception.AvailableException;
import ru.practicum.shareit.handler.exception.ForbiddenException;
import ru.practicum.shareit.handler.exception.UnsupportedStatusException;
import ru.practicum.shareit.handler.exception.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Override
    public BookingDtoSuccess createBooking(BookingDto bookingDto, Long userId) {
        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setBooker(userId);
        booking.setStatus(BookingStatus.WAITING);
        User user = userRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(booking.getItemId()).orElseThrow();
        if (booking.getStart().isAfter(booking.getEnd()))
            throw new ValidationException("The booking start date cannot be later than the end");
        if (!item.getIsAvailable()) {
            throw new AvailableException(String.format("Item with id = %s is not available", item.getId()));
        }
        return BookingMapper.toDtoSuccess(bookingRepository.save(booking), item, user);
    }

    @Override
    public BookingDtoSuccess updateBooking(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(booking.getItemId()).orElseThrow();
        if (!item.getOwner().equals(user)) {
            throw new ForbiddenException(String.format("The user id = %s is not the owner of the item id = %s",
                user.getId(), item.getId()));
        }
        booking.setStatus(BookingStatus.APPROVED);
        return BookingMapper.toDtoSuccess(bookingRepository.save(booking),
            item,
            userRepository.findById(booking.getBooker()).orElseThrow());
    }

    @Override
    public BookingDtoSuccess getBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Item item = itemRepository.findById(booking.getItemId()).orElseThrow();
        if (!userId.equals(booking.getBooker()) && !userId.equals(item.getOwner().getId())) {
            throw new ForbiddenException("The user is not the owner or booker");
        }
        return BookingMapper.toDtoSuccess(bookingRepository.findById(bookingId).orElseThrow(),
            item,
            userRepository.findById(booking.getBooker()).orElseThrow());
    }

    @Override
    public List<BookingDtoSuccess> getBookingCurrentUser(Long userId, String state) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Booking> bookings;
        switch (state) {
            case "ALL" : bookings = bookingRepository.getAllUsersBooking(userId); break;
            case "CURRENT" : bookings = bookingRepository.getCurrentUsersBooking(userId); break;
            case "PAST" : bookings = bookingRepository.getPastUsersBooking(userId); break;
            case "FUTURE" : bookings = bookingRepository.getFutureUsersBooking(userId); break;
            case "WAITING" :
            case "REJECTED" :
                bookings = bookingRepository.getWaitingOrRejectedUsersBooking(userId, state); break;
            default: throw new UnsupportedStatusException(String.format("Unknown state: %s", state));
        }
        List<BookingDtoSuccess> bookingDtoSuccesses = new ArrayList<>();
        for (Booking booking : bookings) {
            Item item = itemRepository.findById(booking.getItemId()).orElseThrow();
            bookingDtoSuccesses.add(BookingMapper.toDtoSuccess(booking, item, user));
        }
        return bookingDtoSuccesses;
    }

    @Override
    public List<BookingDtoSuccess> getBookingOwner(Long userId, String state) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Booking> bookings;
        switch (state) {
            case "ALL" : bookings = bookingRepository.getAllOwnerBooking(userId); break;
            case "CURRENT" : bookings = bookingRepository.getCurrentOwnerBooking(userId); break;
            case "PAST" : bookings = bookingRepository.getPastOwnerBooking(userId); break;
            case "FUTURE" : bookings = bookingRepository.getFutureOwnerBooking(userId); break;
            case "WAITING" :
            case "REJECTED" :
                bookings = bookingRepository.getWaitingOrRejectedOwnerBooking(userId, state); break;
            default: throw new UnsupportedStatusException(String.format("Unknown state: %s", state));
        }
        List<BookingDtoSuccess> bookingDtoSuccesses = new ArrayList<>();
        for (Booking booking : bookings) {
            Item item = itemRepository.findById(booking.getItemId()).orElseThrow();
            bookingDtoSuccesses.add(BookingMapper.toDtoSuccess(booking, item, user));
        }
        return bookingDtoSuccesses;
    }
}
