package com.exemple.socialmedia.repositories;

import com.exemple.socialmedia.domain.Post.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
  Page<Comment> findByUserId(UUID id, Pageable pageable);

  Page<Comment> findByPostId(Integer id, Pageable pageable);
}
