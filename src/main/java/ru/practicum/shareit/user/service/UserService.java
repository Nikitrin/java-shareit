package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Integer userid);

    UserDto updateUser(Integer userId, UserDto userDto);

    UserDto deleteUser(Integer userId);

    List<UserDto> getAllUsers();
}
