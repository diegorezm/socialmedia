package com.exemple.socialmedia.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
  @Min(value = 4, message = "Content must be at least 4 characters long")
  @Max(value = 1150, message = "Content must be at most 1150 characters long")
  private String content;

  @Nullable()
  @URL()
  private String image;

  @Positive(message = "Likes must be a positive number")
  private Integer likes;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments;

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
