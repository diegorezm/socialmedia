package com.exemple.socialmedia.services;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.User.UserLikes;
import com.exemple.socialmedia.repositories.PostsRepository;
import com.exemple.socialmedia.repositories.UserLikesRepository;
import com.exemple.socialmedia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserLikesService {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLikesRepository userLikesRepository;

    public void create(Integer postId, UUID userId) {
        var userLike = this.userLikesRepository.findByUserIdAndPostId(userId, postId);
        if (userLike.isPresent()) {
            throw new HttpException(HttpStatus.CONFLICT);
        }
        var post = this.postsRepository.findById(postId)
                .orElseThrow(() -> new HttpException("Post not found.", HttpStatus.NOT_FOUND));
        var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new HttpException("User not found.", HttpStatus.NOT_FOUND));
        post.setLikes(post.getLikes() + 1);
        this.userLikesRepository.save(new UserLikes(user, post));
        this.postsRepository.save(post);
    }

    public void remove(Integer postId, UUID userId) {
        var post = this.postsRepository.findById(postId)
                .orElseThrow(() -> new HttpException("Post not found.", HttpStatus.NOT_FOUND));
        post.setLikes(post.getLikes() - 1);
        var userLike = this.userLikesRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND));
        this.userLikesRepository.delete(userLike);
        this.postsRepository.save(post);
    }
}