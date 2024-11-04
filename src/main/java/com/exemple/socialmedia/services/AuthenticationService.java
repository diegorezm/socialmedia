package com.exemple.socialmedia.services;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.domain.User.UserDTO;
import com.exemple.socialmedia.domain.User.UserResponseDTO;
import com.exemple.socialmedia.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserResponseDTO register(UserDTO payload) {
        if (this.userRepository.existsByEmail(payload.email())) {
            throw new HttpException("This email already exists.", HttpStatus.CONFLICT);
        }
        String password = new BCryptPasswordEncoder().encode(payload.password());
        User user = new User(payload);
        user.setPassword(password);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        var newUser = this.userRepository.save(user);
        return newUser.toSafeResponse();
    }

    public UserResponseDTO getUserFromHeader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return user.toSafeResponse();
        }
        throw new HttpException("User not found.", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this email " + username + " ."));
    }
}
