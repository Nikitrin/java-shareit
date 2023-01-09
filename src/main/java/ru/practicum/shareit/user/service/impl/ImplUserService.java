package ru.practicum.shareit.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImplUserService implements UserService {
    private UserStorage userStorage;

    public UserDto createUser(UserDto userDto) {
        User user = UserDto.toUser(userDto);
        return UserDto.toUserDto(userStorage.createUser(user));
    }

    public UserDto getUserById(Integer userid) {
        return UserDto.toUserDto(userStorage.getUserById(userid));
    }

    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = UserDto.toUser(userDto);
        user.setId(userId);
        return UserDto.toUserDto(userStorage.updateUser(user));
    }

    public UserDto deleteUser(Integer userId) {
        return UserDto.toUserDto(userStorage.deleteUserById(userId));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userStorage.getAllUsers();
        return users.stream().map(UserDto::toUserDto).collect(Collectors.toList());
    }
}
