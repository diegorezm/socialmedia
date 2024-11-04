package com.exemple.socialmedia.controllers;

import com.exemple.socialmedia.domain.Post.Comment;
import com.exemple.socialmedia.domain.Post.Post;
import com.exemple.socialmedia.domain.Post.PostDTO;
import com.exemple.socialmedia.domain.Post.filters.PostFilters;
import com.exemple.socialmedia.domain.Response.ApiResponse;
import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.services.PostService;
import com.exemple.socialmedia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<Post> getPosts(@RequestParam(required = false) String search,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            Pageable pageable) {
        PostFilters filter = new PostFilters();
        filter.setSearch(search);
        filter.setUserId(userId);
        filter.setOrderBy(orderBy);
        filter.setDirection(direction);
        return postService.getAll(pageable, filter);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getById(@PathVariable Integer postId) {
        return ResponseEntity.ok().body(this.postService.getById(postId));
    }

    @GetMapping("/{postId}/comments")
    public Page<Comment> getComments(@PathVariable Integer postId, Pageable pageable) {
        return this.postService.getCommentsByPostId(postId, pageable);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody PostDTO postDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.postService.create(postDTO, user.getId());
        return ResponseEntity.ok(new ApiResponse("Post created successfully."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer postId, @RequestBody PostDTO postDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.postService.update(postDTO, user.getId(), postId);
        return ResponseEntity.ok(new ApiResponse("Post updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = this.userService.getByEmail(email);
        this.postService.delete(user.getId(), postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted successfully."));
    }

}
