package com.exemple.socialmedia.domain.Post;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import com.exemple.socialmedia.domain.User.User;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "posts")
@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
public class Post {
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "Content cannot be blank")
  @Size(min = 4, max = 1150, message = "Your content length should be between 4 and 1150")
  private String content;

  @Nullable()
  @URL()
  private String image;

  @NotNull
  @DecimalMin(value = "0", message = "Likes must be a positive number")
  private Integer likes;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime updatedAt;

  public Post(String content) {
    this.content = content;
  }

  public Post(PostDTO payload) {
    this.content = payload.content();
    this.image = payload.imgUrl();
  }
}
