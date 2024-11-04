package com.exemple.socialmedia.services;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.Post.Comment;
import com.exemple.socialmedia.domain.Post.Post;
import com.exemple.socialmedia.domain.User.User;
import com.exemple.socialmedia.domain.User.UserDTO;
import com.exemple.socialmedia.domain.User.UserLikes;
import com.exemple.socialmedia.repositories.CommentRepository;
import com.exemple.socialmedia.repositories.PostsRepository;
import com.exemple.socialmedia.repositories.UserLikesRepository;
import com.exemple.socialmedia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service()
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLikesRepository userLIkesRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostsRepository postsRepository;

    public User getById(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new HttpException("User not found.", HttpStatus.NOT_FOUND));
    }

    public User getByEmail(String email) {
        return this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new HttpException("User not found.", HttpStatus.NOT_FOUND));
    }

    public void update(UUID id, UserDTO payload) {
        var user = this.getById(id);
        user.setName(payload.name());
        user.setEmail(payload.email());
        user.setUpdatedAt(LocalDateTime.now());
        this.userRepository.save(user);
    }

    public void delete(UUID id) {
        if (!this.userRepository.existsById(id)) {
            throw new HttpException("User not found.", HttpStatus.NOT_FOUND);
        }
        this.userRepository.deleteById(id);
    }

    public Page<UserLikes> getUserLikes(UUID id, Pageable pageable) {
        if (!this.userRepository.existsById(id)) {
            throw new HttpException("User not found.", HttpStatus.NOT_FOUND);
        }
        return this.userLIkesRepository.findByUserId(id, pageable);
    }

    public Page<Comment> getUserComments(UUID id, Pageable pageable) {
        if (!this.userRepository.existsById(id)) {
            throw new HttpException("User not found.", HttpStatus.NOT_FOUND);
        }
        return this.commentRepository.findByUserId(id, pageable);
    }

    public Page<Post> getUserPosts(UUID id, Pageable pageable) {
        if (!this.userRepository.existsById(id)) {
            throw new HttpException("User not found.", HttpStatus.NOT_FOUND);
        }
        return this.postsRepository.findByUserId(id, pageable);
    }
}
