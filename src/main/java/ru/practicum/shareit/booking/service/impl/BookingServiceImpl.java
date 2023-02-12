package ru.practicum.shareit.booking.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.common.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoSuccess;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.handler.exception.AvailableException;
import ru.practicum.shareit.handler.exception.UnsupportedStatusException;
import ru.practicum.shareit.handler.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Override
    public BookingDtoSuccess createBooking(BookingDto bookingDto, Long userId) {
        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setStatus(BookingStatus.WAITING.toString());
        User user = userRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow();
        booking.setBooker(user);
        booking.setItem(item);
        if (booking.getItem().getOwner().getId().equals(userId))
            throw new NoSuchElementException("Owner cannot booking his item");
        if (booking.getStart().isAfter(booking.getEnd()))
            throw new ValidationException("The booking start date cannot be later than the end");
        if (!item.getIsAvailable())
            throw new AvailableException(String.format("Item with id = %s is not available", item.getId()));
        return BookingMapper.toDtoOut(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoSuccess updateBooking(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow();
        if (!item.getOwner().getId().equals(userId))
            throw new NoSuchElementException(String.format("The user id = %s is not the owner of the item id = %s",
                userId, item.getId()));
        if (booking.getStatus().equals(BookingStatus.APPROVED.toString()))
            throw new AvailableException(String.format("The booking id = %s is already approved = %s",
                bookingId, approved));
        if (approved) booking.setStatus(BookingStatus.APPROVED.toString());
        else booking.setStatus(BookingStatus.REJECTED.toString());
        return BookingMapper.toDtoOut(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoSuccess getBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow();
        if (!userId.equals(booking.getBooker().getId()) && !userId.equals(item.getOwner().getId())) {
            throw new NoSuchElementException("The user is not the owner or booker");
        }
        return BookingMapper.toDtoOut(bookingRepository.findById(bookingId).orElseThrow());
    }

    @Override
    public List<BookingDtoSuccess> getBookingCurrentUser(Long userId, String state, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow();
        List<Booking> bookings;
        switch (state) {
            case "ALL":
                bookings = bookingRepository.getAllUsersBooking(userId, PageRequest.of(from, size));
                break;
            case "CURRENT":
                bookings = bookingRepository.getCurrentUsersBooking(userId, PageRequest.of(from, size));
                break;
            case "PAST":
                bookings = bookingRepository.getPastUsersBooking(userId, PageRequest.of(from, size));
                break;
            case "FUTURE":
                bookings = bookingRepository.getFutureUsersBooking(userId, PageRequest.of(from, size));
                break;
            case "WAITING":
                bookings = bookingRepository.getWaitingUsersBooking(userId, PageRequest.of(from, size));
                break;
            case "REJECTED":
                bookings = bookingRepository.getRejectedUsersBooking(userId, PageRequest.of(from, size));
                break;
            default:
                throw new UnsupportedStatusException(String.format("Unknown state: %s", state));
        }
        List<BookingDtoSuccess> bookingDtoSuccesses = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDtoSuccesses.add(BookingMapper.toDtoOut(booking));
        }
        return bookingDtoSuccesses;
    }

    @Override
    public List<BookingDtoSuccess> getBookingOwner(Long userId, String state, Integer from, Integer size) {
        userRepository.findById(userId).orElseThrow();
        List<Booking> bookings;
        switch (state) {
            case "ALL":
                bookings = bookingRepository.getAllOwnerBooking(userId, PageRequest.of(from, size));
                break;
            case "CURRENT":
                bookings = bookingRepository.getCurrentOwnerBooking(userId, PageRequest.of(from, size));
                break;
            case "PAST":
                bookings = bookingRepository.getPastOwnerBooking(userId, PageRequest.of(from, size));
                break;
            case "FUTURE":
                bookings = bookingRepository.getFutureOwnerBooking(userId, PageRequest.of(from, size));
                break;
            case "WAITING":
                bookings = bookingRepository.getWaitingOwnerBooking(userId, PageRequest.of(from, size));
                break;
            case "REJECTED":
                bookings = bookingRepository.getRejectedOwnerBooking(userId, PageRequest.of(from, size));
                break;
            default:
                throw new UnsupportedStatusException(String.format("Unknown state: %s", state));
        }
        List<BookingDtoSuccess> bookingDtoSuccesses = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDtoSuccesses.add(BookingMapper.toDtoOut(booking));
        }
        return bookingDtoSuccesses;
    }
}
