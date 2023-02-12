package ru.practicum.shareit.item.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.handler.exception.AvailableException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentShort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private BookingRepository bookingRepository;
    private CommentRepository commentRepository;

    @Override
    public CommentShort createComment(CommentDto commentDto, Long userId, Long itemId) {
        User user = userRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        List<Booking> bookings = bookingRepository.getPastUsersBooking(userId,
            PageRequest.of(0, Integer.MAX_VALUE));
        for (Booking booking : bookings) {
            if (booking.getItem().getId().equals(itemId)) {
                Comment comment = CommentMapper.toComment(commentDto);
                comment.setAuthor(user);
                comment.setItem(item);
                comment.setCreated(LocalDateTime.now());
                return CommentMapper.toCommentShort(commentRepository.save(comment));
            }
        }
        throw new AvailableException(
            String.format("this user id = %s did not booking the item id = %s", userId, itemId));
    }
}
