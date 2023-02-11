package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.handler.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUser_whenUserValid_thenReturnUser() throws Exception {
        UserDto sentUser = new UserDto("name", "email@email.ru");
        UserDto savedUser = new UserDto(1L, "name", "email@email.ru");
        when(userService.createUser(sentUser)).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(sentUser))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(savedUser.getId()), Long.class))
            .andExpect(jsonPath("$.name", is(savedUser.getName())))
            .andExpect(jsonPath("$.email", is(savedUser.getEmail())));
    }

    @Test
    void createUser_whenUserNotValidName_thenReturnError() throws Exception {
        UserDto sentUser = new UserDto("", "email@email.ru");

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(sentUser))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void createUser_whenUserNotValidEmail_thenReturnError() throws Exception {
        UserDto sentUser = new UserDto("name", "email.ru");

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(sentUser))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void getUserById_whenUserExists_thenReturnUser() throws Exception {
        Long userId = 1L;
        UserDto savedUser = new UserDto(userId, "name", "email@email.ru");
        when(userService.getUserById(userId)).thenReturn(savedUser);

        mockMvc.perform(get("/users/" + userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(savedUser.getId()), Long.class))
            .andExpect(jsonPath("$.name", is(savedUser.getName())))
            .andExpect(jsonPath("$.email", is(savedUser.getEmail())));
    }

    @Test
    void getUserById_whenUserNotFound_thenThrowException() throws Exception {
        Long userId = 1L;
        doThrow(UserNotFoundException.class).when(userService).getUserById(userId);

        mockMvc.perform(get("/users/" + userId))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void updateUser_whenUserExists_thenReturnUser() throws Exception {
        Long userId = 1L;
        UserDto updatedUser = new UserDto("new name", "newemail@email.ru");
        UserDto savedUser = new UserDto(userId, "new name", "newemail@email.ru");
        when(userService.updateUser(userId, updatedUser)).thenReturn(savedUser);

        mockMvc.perform(patch("/users/" + userId)
                .content(mapper.writeValueAsString(updatedUser))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(savedUser.getId()), Long.class))
            .andExpect(jsonPath("$.name", is(savedUser.getName())))
            .andExpect(jsonPath("$.email", is(savedUser.getEmail())));
    }

    @Test
    void updateUser_whenUserNotFound_thenThrowException() throws Exception {
        Long userId = 1L;
        UserDto updatedUser = new UserDto("new name", "newemail@email.ru");
        doThrow(UserNotFoundException.class).when(userService).updateUser(userId, updatedUser);

        mockMvc.perform(patch("/users/" + userId)
                .content(mapper.writeValueAsString(updatedUser))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteUser_whenUserExists_thenReturnStatusOk() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/users/" + userId))
            .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void deleteUser_whenUserNotFound_thenThrowException() throws Exception {
        Long userId = 1L;

        doThrow(UserNotFoundException.class).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/" + userId))
            .andExpect(status().is4xxClientError());

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void getAllUsers_whenInvoke_thenReturnListOfUsers() throws Exception {
        List<UserDto> users = List.of(new UserDto(), new UserDto());

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }
}