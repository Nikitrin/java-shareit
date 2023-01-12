package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    User getUserById(Integer userId);

    User updateUser(User user);

    User deleteUserById(Integer userId);

    List<User> getAllUsers();
}
