package com.exemple.socialmedia.domain.Post;

import java.util.UUID;

public record CommentDTO(
    String content,
    UUID userId,
    Integer postId) {
}
