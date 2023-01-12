package ru.practicum.shareit.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
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
        User user = UserMapper.toUser(userDto);
        return UserMapper.toDto(userStorage.createUser(user));
    }

    public UserDto getUserById(Integer userid) {
        return UserMapper.toDto(userStorage.getUserById(userid));
    }

    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setId(userId);
        return UserMapper.toDto(userStorage.updateUser(user));
    }

    public UserDto deleteUser(Integer userId) {
        return UserMapper.toDto(userStorage.deleteUserById(userId));
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userStorage.getAllUsers();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
