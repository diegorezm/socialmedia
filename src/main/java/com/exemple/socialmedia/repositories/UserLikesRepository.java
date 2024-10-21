package com.exemple.socialmedia.repositories;

import com.exemple.socialmedia.domain.User.UserLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserLikesRepository extends JpaRepository<UserLikes, Integer> {
    Page<UserLikes> findByUserId(UUID id, Pageable pageable);

    Page<UserLikes> findByPostId(Integer id, Pageable pageable);

    Optional<UserLikes> findByUserIdAndPostId(UUID userId, Integer postId);
}
