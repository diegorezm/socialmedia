package com.exemple.socialmedia.domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(
    String email,
    String name,
    @NotNull @NotBlank(message = "Password cannot be blank") @Size(min = 6, max = 12, message = "Password must be between 6 and 16 characters long") String password,
    String imgUrl) {
}
