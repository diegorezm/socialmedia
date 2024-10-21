 package com.exemple.socialmedia.services;

import com.exemple.socialmedia.domain.Auth.LoginDTO;
import com.exemple.socialmedia.domain.Auth.LoginResponseDTO;
import com.exemple.socialmedia.domain.Auth.TokenResponseDTO;
import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.domain.User.UserDTO;
import com.exemple.socialmedia.domain.User.UserResponseDTO;
import com.exemple.socialmedia.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserResponseDTO register(UserDTO payload) {
        if (this.userRepository.existsByEmail(payload.email())) {
            throw new HttpException("This email already exists.", HttpStatus.CONFLICT);
        }
        String password = new BCryptPasswordEncoder().encode(payload.password());
        User user = new User(payload);
        user.setPassword(password);
        var newUser = this.userRepository.save(user);
        return newUser.toSafeResponse();
    }

    public LoginResponseDTO login(LoginDTO payload) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(payload.email(),
                payload.password());
        var auth = this.authenticationManager.authenticate(authenticationToken);
        User user = (User) auth.getPrincipal();
        var token = this.tokenService.genToken(user);
        var tokenResponse = new TokenResponseDTO(token.token(), token.expiresAt());
        return new LoginResponseDTO(tokenResponse, user.toSafeResponse());
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this email " + username));
    }
}
