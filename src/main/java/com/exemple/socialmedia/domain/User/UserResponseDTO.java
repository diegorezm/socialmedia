package com.exemple.socialmedia.domain.User;

import java.time.LocalDateTime;

public record UserResponseDTO(
  String name,
  String email,
  String imgUrl,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {
}
