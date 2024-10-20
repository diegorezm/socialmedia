package com.exemple.socialmedia.services;

import com.exemple.socialmedia.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service()
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
}
