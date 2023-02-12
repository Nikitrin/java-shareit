package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.handler.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;

    @Test
    void getUserById_whenUserFound_thenReturnUser() {
        Long userId = 1L;
        UserDto expectedUser = new UserDto(userId, "Name", "email@email.ru");
        User savedUser = new User(userId, "Name", "email@email.ru");
        when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));
        when(userMapper.toDto(savedUser)).thenReturn(expectedUser);

        UserDto actualUser = userService.getUserById(userId);

        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toDto(savedUser);
    }

    @Test
    void getUserById_whenUserNotFound_thenThrowUserNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void createUser_whenUserValid_thenReturnUser() {
        User createdUser = new User("Name", "email@email.ru");
        User expectedUser = new User(1L, "Name", "email@email.ru");
        UserDto createdUserDto = new UserDto("Name", "email@email.ru");
        UserDto expectedUserDto = new UserDto(1L, "Name", "email@email.ru");

        when(userRepository.save(createdUser)).thenReturn(expectedUser);
        when(userMapper.toDto(expectedUser)).thenReturn(expectedUserDto);
        when(userMapper.toUser(createdUserDto)).thenReturn(createdUser);

        UserDto actualUserDto = userService.createUser(createdUserDto);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void updateUser_whenUpdateNameAndEmail_thenReturnUser() {
        Long userId = 1L;
        User oldUser = new User(userId, "Name", "email@email.ru");
        User expectedUser = new User(userId, "newName", "newemail@email.ru");
        UserDto updatedUserDto = new UserDto("newName", "newemail@email.ru");
        UserDto expectedUserDto = new UserDto(userId, "newName", "newemail@email.ru");

        when(userRepository.findById(userId)).thenReturn(Optional.of(oldUser));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(userMapper.toDto(expectedUser)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.updateUser(userId, updatedUserDto);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void deleteUser_whenUserNotFound_thenThrowUserNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void deleteUser_whenUserFound_thenNothing() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(new User(), new User());
        List<UserDto> usersDto = List.of(new UserDto(), new UserDto());
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(users)).thenReturn(usersDto);

        assertEquals(usersDto, userService.getAllUsers());
    }
}