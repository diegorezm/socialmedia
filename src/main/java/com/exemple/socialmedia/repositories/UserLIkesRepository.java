package com.exemple.socialmedia.repositories;

import com.exemple.socialmedia.domain.User.UserLikes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserLIkesRepository extends JpaRepository<UserLikes, Integer> {
  List<UserLikes> findByUserId(UUID id, Pageable pageable);

  List<UserLikes> findByPostId(Integer id, Pageable pageable);
}
