package com.exemple.socialmedia.controllers;

import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.domain.User.UserLikes;
import com.exemple.socialmedia.services.UserLikesService;
import com.exemple.socialmedia.services.UserService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
public class UserLikesController {
    @Autowired
    private UserLikesService userLikesService;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}")
    public Page<UserLikes> getByUserId(@PathVariable UUID userId, Pageable pageable) {
        return this.userLikesService.getByUserId(userId, pageable);
    }

    @GetMapping("/posts/{postId}")
    public Page<UserLikes> getByPostId(@PathVariable Integer postId, Pageable pageable) {
        return this.userLikesService.getByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}")
    public void create(@PathVariable Integer postId, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.userLikesService.create(postId, user.getId());
    }

    @DeleteMapping("/posts/{postId}")
    public void remove(@PathVariable Integer postId, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.userLikesService.remove(postId, user.getId());
    }
}
