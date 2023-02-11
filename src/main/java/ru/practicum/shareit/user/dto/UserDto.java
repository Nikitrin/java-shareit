package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.common.UserMarker;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Min(1)
    private Long id;
    @NotBlank(message = "Name can't be blank or null", groups = {UserMarker.OnCrate.class})
    @Size(min = 1, max = 50, message = "Max length of name is 50, min length is 1 character",
        groups = {UserMarker.OnCrate.class})
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces", groups = {UserMarker.OnCrate.class})
    private String name;
    @NotBlank(message = "Email can't be blank or null", groups = {UserMarker.OnCrate.class})
    @Email(message = "Invalid email", groups = {UserMarker.OnCrate.class})
    private String email;
}
