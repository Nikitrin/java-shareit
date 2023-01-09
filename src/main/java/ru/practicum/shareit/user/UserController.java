package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Create user: name = {}, email = {}", userDto.getName(), userDto.getEmail());
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) {
        log.info("Get user by id = {}", userId);
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        log.info("Update user by id = {}, new data: name = {}, email = {}",
                userId, userDto.getName(), userDto.getEmail());
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public UserDto deleteUser(@PathVariable Integer userId) {
        log.info("Delete user by id = {}", userId);
        return userService.deleteUser(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Get all users");
        return userService.getAllUsers();
    }
}
