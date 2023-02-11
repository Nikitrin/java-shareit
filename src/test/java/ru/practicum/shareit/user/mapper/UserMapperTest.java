package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    private final User user = new User(1L, "Name", "email@email.ru");
    private final UserDto userDto = new UserDto(1L, "Name", "email@email.ru");

    @Test
    void toDto() {
        assertEquals(userDto, userMapper.toDto(user));
    }

    @Test
    void toUser() {
        assertEquals(user, userMapper.toUser(userDto));
    }

    @Test
    void testToDto() {
        assertEquals(List.of(userDto), userMapper.toDto(List.of(user)));
    }

    @Test
    void testToUser() {
        assertEquals(List.of(user), userMapper.toUser(List.of(userDto)));
    }
}