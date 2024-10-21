package com.exemple.socialmedia.controllers;

import com.exemple.socialmedia.domain.Auth.LoginDTO;
import com.exemple.socialmedia.domain.Auth.LoginResponseDTO;
import com.exemple.socialmedia.domain.User.UserDTO;
import com.exemple.socialmedia.domain.User.UserResponseDTO;
import com.exemple.socialmedia.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> profile() {
        return ResponseEntity.ok().body(this.authenticationService.getUserFromHeader());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(201).body(this.authenticationService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok().body(this.authenticationService.login(loginDTO));
    }
}
