package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Name can't be blank or null")
    @Size(min = 1, max = 50, message = "Max length of name is 50, min length is 1 character")
    @Pattern(regexp = "^\\S*$", message = "Login cannot contain spaces")
    private String name;
    @NotBlank(message = "Email can't be blank or null")
    @Email(message = "Invalid email")
    private String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
