package com.exemple.socialmedia.domain.User;

import java.util.UUID;

public record UserLikeDTO(
  UUID userId,
  Integer postId
) {
}
