package com.exemple.socialmedia.repositories;

import com.exemple.socialmedia.domain.Post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostsRepository extends JpaRepository<Post, Integer> {
  Page<Post> findByUserId(UUID id, Pageable pageable);
}
