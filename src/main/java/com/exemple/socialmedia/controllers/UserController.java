package com.exemple.socialmedia.controllers;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.Response.ApiResponse;
import com.exemple.socialmedia.domain.User.UserDTO;
import com.exemple.socialmedia.domain.User.UserResponseDTO;
import com.exemple.socialmedia.services.AuthenticationService;
import com.exemple.socialmedia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(this.userService.getById(id).toSafeResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        if (this.authenticationService.getUserFromHeader().id().equals(id)) {
            this.userService.update(id, userDTO);
            return ResponseEntity.ok().body(new ApiResponse("User updated!"));
        }
        throw new HttpException(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        if (this.authenticationService.getUserFromHeader().id().equals(id)) {
            this.delete(id);
            return ResponseEntity.ok().body(new ApiResponse("User deleted."));
        }
        throw new HttpException(HttpStatus.UNAUTHORIZED);
    }
}
