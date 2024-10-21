package com.exemple.socialmedia.services;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.Post.Comment;
import com.exemple.socialmedia.domain.Post.Post;
import com.exemple.socialmedia.domain.Post.PostDTO;
import com.exemple.socialmedia.repositories.CommentRepository;
import com.exemple.socialmedia.repositories.PostsRepository;
import com.exemple.socialmedia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Post> getAll(Pageable pageable) {
        return this.postsRepository.findAll(pageable);
    }

    public Post getById(Integer id) {
        return this.postsRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND));
    }

    public Page<Comment> getCommentsByPostId(Integer postId, Pageable pageable) {
        return this.commentRepository.findByPostId(postId, pageable);
    }

    public void create(PostDTO payload, UUID userId) {
        var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new HttpException("User not found.", HttpStatus.NOT_FOUND));
        Post post = new Post(payload);
        post.setUser(user);
        this.postsRepository.save(post);
    }

    public void update(PostDTO payload, UUID userId, Integer postId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new HttpException("User not found.", HttpStatus.NOT_FOUND));
        var post = this.getById(postId);
        if (post.getUser().getId().equals(userId)) {
            post.setContent(payload.content());
            post.setImage(payload.imgUrl());
            this.postsRepository.save(post);
        }
        throw new HttpException("Not authorized.", HttpStatus.FORBIDDEN);
    }

    public void delete(UUID userId, Integer postId) {
        var post = this.getById(postId);
        if (post.getUser().getId().equals(userId)) {
            this.postsRepository.deleteById(postId);
        }
        throw new HttpException("Not authorized.", HttpStatus.FORBIDDEN);
    }
}
