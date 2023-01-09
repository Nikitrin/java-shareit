package ru.practicum.shareit.user.storage.impl;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.handler.exception.NotFoundException;
import ru.practicum.shareit.handler.exception.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<String, Integer> emails = new HashMap<>();
    private Integer id = 1;

    @Override
    public User createUser(User user) {
        if (emails.containsKey(user.getEmail())) {
            throw new ValidationException("The email is already registered");
        }
        user.setId(id);
        users.put(id, user);
        emails.put(user.getEmail(), id);
        id++;
        return user;
    }

    @Override
    public User getUserById(Integer userId) {
        if (userId != null && !users.containsKey(userId)) {
            throw new NotFoundException("The user not found by id");
        }
        return users.get(userId);
    }

    @Override
    public User updateUser(User updateUser) {
        if (!users.containsKey(updateUser.getId())) {
            throw new NotFoundException("The user not found by id");
        }
        User user = users.get(updateUser.getId());
        if (updateUser.getName() != null && !updateUser.getName().isBlank()) {
            user.setName(updateUser.getName());
        }
        if (updateUser.getEmail() != null && !updateUser.getEmail().isBlank()) {
            if (!user.getEmail().equals(updateUser.getEmail())) {
                if (emails.containsKey(updateUser.getEmail())) {
                    throw new ValidationException("The email is already registered another user");
                }
                emails.remove(user.getEmail());
                emails.put(updateUser.getEmail(), updateUser.getId());
                user.setEmail(updateUser.getEmail());
            }
        }
        return user;
    }

    @Override
    public User deleteUserById(Integer userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("The user not found by id");
        }
        emails.remove(users.get(userId).getEmail());
        return users.remove(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
