package com.exemple.socialmedia.services;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.Post.Comment;
import com.exemple.socialmedia.domain.Post.CommentDTO;
import com.exemple.socialmedia.repositories.CommentRepository;
import com.exemple.socialmedia.repositories.PostsRepository;
import com.exemple.socialmedia.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostsRepository postsRepository;

  @Autowired
  private CommentRepository commentRepository;

  public Page<Comment> getAllByPostId(Integer postId, Pageable pageable) {
    return this.commentRepository.findByPostId(postId, pageable);
  }

  public Page<Comment> getAllByUserId(UUID userId, Pageable pageable) {
    return this.commentRepository.findByUserId(userId, pageable);
  }

  public void create(CommentDTO payload, UUID userId) {
    var post = this.postsRepository.findById(payload.postId())
        .orElseThrow(() -> new HttpException("Post not found.", HttpStatus.NOT_FOUND));
    var user = this.userRepository.findById(userId)
        .orElseThrow(() -> new HttpException("User not found.", HttpStatus.NOT_FOUND));
    Comment comment = new Comment(user, post, payload.content());
    comment.setCreatedAt(LocalDateTime.now());
    comment.setUpdatedAt(LocalDateTime.now());
    this.commentRepository.save(comment);
  }

  public void update(CommentDTO payload, Integer commentId, UUID userId) {
    if (!this.userRepository.existsById(userId)) {
      throw new HttpException("User not found.", HttpStatus.NOT_FOUND);
    }
    var comment = this.commentRepository.findById(commentId)
        .orElseThrow(() -> new HttpException("Comment not found.", HttpStatus.NOT_FOUND));

    if (!comment.getUser().getId().equals(userId)) {
      throw new HttpException("You are not authorized to update this comment.", HttpStatus.FORBIDDEN);
    }
    comment.setContent(payload.content());
    comment.setUpdatedAt(LocalDateTime.now());
    this.commentRepository.save(comment);
  }

  public void remove(Integer commentId, UUID userId) {
    Comment comment = this.commentRepository.findById(commentId)
        .orElseThrow(() -> new HttpException("Comment not found.", HttpStatus.NOT_FOUND));
    if (!comment.getUser().getId().equals(userId)) {
      throw new HttpException("You are not authorized to delete this comment.", HttpStatus.FORBIDDEN);
    }
    this.commentRepository.deleteById(commentId);
  }
}
