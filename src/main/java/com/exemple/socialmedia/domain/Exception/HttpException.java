package com.exemple.socialmedia.domain.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpException extends RuntimeException {
  private int status;

  public HttpException(int status) {
    super();
    this.status = status;
  }

  public HttpException(String message, int status) {
    super(message);
    this.status = status;
  }

  public HttpException() {
    super("Something went wrong.");
    this.status = 500;
  }
}
