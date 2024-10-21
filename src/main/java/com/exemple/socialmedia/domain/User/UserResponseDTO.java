package com.exemple.socialmedia.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String imgUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
