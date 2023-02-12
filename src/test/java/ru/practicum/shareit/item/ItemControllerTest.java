package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.handler.exception.ItemNotFoundException;
import ru.practicum.shareit.handler.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.CommentShort;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @MockBean
    private CommentService commentService;

    @Test
    void createItem_whenValidItemAndUser_thenReturnItem() throws Exception {
        Long itemId = 1L;
        Long userId = 1L;
        ItemDto requestItem = new ItemDto("item", "description", true);
        ItemDto expectedItem = new ItemDto(itemId, "item", "description", true);

        when(itemService.createItem(requestItem, userId)).thenReturn(expectedItem);

        mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString((requestItem)))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedItem.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(expectedItem.getName())))
                .andExpect(jsonPath("$.description", is(expectedItem.getDescription())))
                .andExpect(jsonPath("$.available", is(expectedItem.getAvailable())));

        verify(itemService, times(1)).createItem(requestItem, userId);
    }

    @Test
    void createItem_whenNotValidUser_thenThrowException() throws Exception {
        Long userId = 100L;
        ItemDto requestItem = new ItemDto("item", "description", true);

        doThrow(UserNotFoundException.class).when(itemService).createItem(requestItem, userId);

        mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString((requestItem)))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(itemService, times(1)).createItem(requestItem, userId);
    }

    @Test
    void createItem_whenNotValidItem_thenThrowException() throws Exception {
        Long userId = 1L;
        ItemDto requestItem = new ItemDto("", "", true);

        mockMvc.perform(post("/items")
                        .content(mapper.writeValueAsString((requestItem)))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateItem_whenValidItemAndUser_thenReturnItem() throws Exception {
        Long itemId = 1L;
        Long userId = 1L;
        ItemDto requestItem = new ItemDto("newItem", "newDescription", true);
        ItemDto expectedItem = new ItemDto(itemId, "newItem", "newDescription", true);

        when(itemService.updateItem(requestItem, itemId, userId)).thenReturn(expectedItem);

        mockMvc.perform(patch("/items/" + itemId)
                        .content(mapper.writeValueAsString((requestItem)))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedItem.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(expectedItem.getName())))
                .andExpect(jsonPath("$.description", is(expectedItem.getDescription())))
                .andExpect(jsonPath("$.available", is(expectedItem.getAvailable())));

        verify(itemService, times(1)).updateItem(requestItem, itemId, userId);
    }

    @Test
    void updateItem_whenNotFoundUser_thenReturnItem() throws Exception {
        Long itemId = 1L;
        Long userId = 100L;
        ItemDto requestItem = new ItemDto("newItem", "newDescription", true);

        doThrow(UserNotFoundException.class).when(itemService).updateItem(requestItem, itemId, userId);

        mockMvc.perform(patch("/items/" + itemId)
                        .content(mapper.writeValueAsString((requestItem)))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("UserNotFoundException")));

        verify(itemService, times(1)).updateItem(requestItem, itemId, userId);
    }

    @Test
    void updateItem_whenNotFoundItem_thenReturnItem() throws Exception {
        Long itemId = 100L;
        Long userId = 1L;
        ItemDto requestItem = new ItemDto("newItem", "newDescription", true);

        doThrow(ItemNotFoundException.class).when(itemService).updateItem(requestItem, itemId, userId);

        mockMvc.perform(patch("/items/" + itemId)
                        .content(mapper.writeValueAsString((requestItem)))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("ItemNotFoundException")));
        ;

        verify(itemService, times(1)).updateItem(requestItem, itemId, userId);
    }

    @Test
    void getItemById_whenValidItemAndUser_thenReturnItem() throws Exception {
        Long itemId = 1L;
        Long userId = 1L;
        ItemDto expectedItem = new ItemDto(itemId, "newItem", "newDescription", true);

        when(itemService.getItemById(itemId, userId)).thenReturn(expectedItem);

        mockMvc.perform(get("/items/" + itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedItem.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(expectedItem.getName())))
                .andExpect(jsonPath("$.description", is(expectedItem.getDescription())))
                .andExpect(jsonPath("$.available", is(expectedItem.getAvailable())));
    }

    @Test
    void getItemById_whenNotFoundUser_thenReturnItem() throws Exception {
        Long itemId = 1L;
        Long userId = 100L;

        doThrow(UserNotFoundException.class).when(itemService).getItemById(itemId, userId);

        mockMvc.perform(get("/items/" + itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("UserNotFoundException")));

        verify(itemService, times(1)).getItemById(itemId, userId);
    }

    @Test
    void getItemById_whenNotFoundItem_thenReturnItem() throws Exception {
        Long itemId = 100L;
        Long userId = 1L;

        doThrow(ItemNotFoundException.class).when(itemService).getItemById(itemId, userId);

        mockMvc.perform(get("/items/" + itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("ItemNotFoundException")));

        verify(itemService, times(1)).getItemById(itemId, userId);
    }

    @Test
    void getAllItems_whenValidUser_thenReturnItemsList() throws Exception {
        Long userId = 1L;

        List<ItemDto> items = List.of(new ItemDto(), new ItemDto());

        when(itemService.getAllItems(userId)).thenReturn(items);

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAllItems_whenNotFoundUser_thenReturnItemsList() throws Exception {
        Long userId = 100L;

        doThrow(UserNotFoundException.class).when(itemService).getAllItems(userId);

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("UserNotFoundException")));

        verify(itemService, times(1)).getAllItems(userId);
    }

    @Test
    void searchItemsByText_whenValidUser_thenReturnItemList() throws Exception {
        Long userId = 1L;
        String text = "text";

        List<ItemDto> items = List.of(new ItemDto(), new ItemDto());

        when(itemService.searchItemsByText(text)).thenReturn(items);

        mockMvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", userId)
                        .param("text", text))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void searchItemsByText_whenUserNotFound_thenReturnItemList() throws Exception {
        Long userId = 1L;
        String text = "text";

        doThrow(UserNotFoundException.class).when(itemService).searchItemsByText(text);

        mockMvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", userId)
                        .param("text", text))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("UserNotFoundException")));

        verify(itemService, times(1)).searchItemsByText(text);
    }

    @Test
    void createComment_whenValidItemAndComment_thenReturnComment() throws Exception {
        Long itemId = 1L;
        Long userId = 1L;
        CommentDto savedComment = new CommentDto("comment");
        CommentShort expectedComment = new CommentShort(1L, "comment", "author", LocalDateTime.now());

        when(commentService.createComment(savedComment, itemId, userId)).thenReturn(expectedComment);

        mockMvc.perform(post("/items/" + itemId + "/comment")
                        .content(mapper.writeValueAsString(savedComment))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedComment.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(expectedComment.getText())))
                .andExpect(jsonPath("$.authorName", is(expectedComment.getAuthorName())))
                .andExpect(jsonPath("$.created",
                        is(expectedComment.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
    }

    @Test
    void createComment_whenNotFoundUser_thenReturnException() throws Exception {
        Long itemId = 1L;
        Long userId = 1L;
        CommentDto savedComment = new CommentDto("comment");

        doThrow(UserNotFoundException.class).when(commentService).createComment(savedComment, itemId, userId);

        mockMvc.perform(post("/items/" + itemId + "/comment")
                        .content(mapper.writeValueAsString(savedComment))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("UserNotFoundException")));

        verify(commentService, times(1)).createComment(savedComment, itemId, userId);
    }

    @Test
    void createComment_whenNotFoundItem_thenReturnException() throws Exception {
        Long itemId = 1L;
        Long userId = 1L;
        CommentDto savedComment = new CommentDto("comment");

        doThrow(ItemNotFoundException.class).when(commentService).createComment(savedComment, itemId, userId);

        mockMvc.perform(post("/items/" + itemId + "/comment")
                        .content(mapper.writeValueAsString(savedComment))
                        .header("X-Sharer-User-Id", userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.error", is("ItemNotFoundException")));

        verify(commentService, times(1)).createComment(savedComment, itemId, userId);
    }
}