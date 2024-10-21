package com.exemple.socialmedia.domain.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.exemple.socialmedia.domain.Post.Comment;
import jakarta.annotation.Nullable;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import com.exemple.socialmedia.domain.Post.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Name cannot be blank")
  @Size(min = 4, max = 255, message = "Name must be between 4 and 255 characters long")
  private String name;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Invalid email address")
  @Size(max = 255, message = "Email must be at most 255 characters long")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters long")
  private String password;

  @Nullable
  @URL
  private String imgUrl;

  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime updatedAt;

  public User(UserDTO payload) {
    this.name = payload.name();
    this.email = payload.email();
    this.password = payload.password();
    this.imgUrl = payload.imgUrl();
  }

  public UserResponseDTO toSafeResponse() {
    return new UserResponseDTO(this.id, this.name, this.email, this.imgUrl, this.createdAt, this.updatedAt);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
