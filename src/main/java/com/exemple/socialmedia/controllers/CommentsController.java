package com.exemple.socialmedia.controllers;

import com.exemple.socialmedia.domain.Post.CommentDTO;
import com.exemple.socialmedia.domain.Response.ApiResponse;
import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.services.CommentService;
import com.exemple.socialmedia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.commentService.create(commentDTO, user.getId());
        return ResponseEntity.ok(new ApiResponse("Comment created successfully."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @RequestBody CommentDTO commentDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.commentService.update(commentDTO, id, user.getId());
        return ResponseEntity.ok(new ApiResponse("Comment updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.commentService.remove(id, user.getId());
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully."));
    }
}
