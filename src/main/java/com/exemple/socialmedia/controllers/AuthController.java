package com.exemple.socialmedia.controllers;

import com.exemple.socialmedia.domain.Auth.LoginDTO;
import com.exemple.socialmedia.domain.Auth.LoginResponseDTO;
import com.exemple.socialmedia.domain.Auth.TokenResponseDTO;
import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.domain.User.UserDTO;
import com.exemple.socialmedia.domain.User.UserResponseDTO;
import com.exemple.socialmedia.services.AuthenticationService;
import com.exemple.socialmedia.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> profile() {
        return ResponseEntity.ok().body(this.authenticationService.getUserFromHeader());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(201).body(this.authenticationService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO payload) {
        return ResponseEntity.ok().body(this.loginHelper(payload));
    }

    private LoginResponseDTO loginHelper(LoginDTO payload) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    payload.email(),
                    payload.password());
            var auth = this.authenticationManager.authenticate(authenticationToken);
            User user = (User) auth.getPrincipal();
            var token = this.tokenService.genToken(user);
            var tokenResponse = new TokenResponseDTO(token.token(), token.expiresAt());
            return new LoginResponseDTO(tokenResponse, user.toSafeResponse());
        } catch (BadCredentialsException e) {
            throw new HttpException("Username or password is incorrect.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new HttpException("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
