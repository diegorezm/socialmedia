package com.exemple.socialmedia.domain.User;

public record UserDTO(
  String email,
  String name,
  String password,
  String imgUrl
) {
}
