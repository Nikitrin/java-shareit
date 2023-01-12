package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.common.UserCreate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDto {
    private Integer id;
    @NotBlank(message = "Name can't be blank or null", groups = {UserCreate.class})
    @Size(min = 1, max = 50, message = "Max length of name is 50, min length is 1 character",
        groups = {UserCreate.class})
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces", groups = {UserCreate.class})
    private String name;
    @NotBlank(message = "Email can't be blank or null", groups = {UserCreate.class})
    @Email(message = "Invalid email", groups = {UserCreate.class})
    private String email;
}
