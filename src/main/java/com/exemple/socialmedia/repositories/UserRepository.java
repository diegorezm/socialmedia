package com.exemple.socialmedia.repositories;

import com.exemple.socialmedia.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findUserByEmail(String email);
}
