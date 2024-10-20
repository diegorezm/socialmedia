package com.exemple.socialmedia.domain.Auth;

import java.time.Instant;

public record TokenResponseDTO(
  String token,
  Instant expiresAt
) {
}
