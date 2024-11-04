package com.exemple.socialmedia.domain.Auth;

import com.exemple.socialmedia.domain.User.UserResponseDTO;

public record LoginResponseDTO(
                TokenResponseDTO token,
                UserResponseDTO user) {
}
