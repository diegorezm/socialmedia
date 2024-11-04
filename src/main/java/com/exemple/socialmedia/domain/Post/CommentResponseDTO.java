package com.exemple.socialmedia.domain.Post;

import java.util.UUID;

public record CommentResponseDTO(
    Integer id,
    String content,
    Integer postId,
    UUID userId,
    String createdAt,
    String updatedAt) {

}
