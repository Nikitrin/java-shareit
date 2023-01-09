package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDto {
    private Integer id;
    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be blank")
    @Size(min = 1, max = 50, message = "Max length of name is 50 characters, min length is 1 character")
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces")
    private String name;
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email")
    private String email;

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }
}
