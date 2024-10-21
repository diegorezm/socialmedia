package com.exemple.socialmedia.domain.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class HttpException extends RuntimeException {
    private HttpStatus status;

    public HttpException(HttpStatus status) {
        super();
        this.status = status;
    }

    public HttpException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpException() {
        super("Something went wrong.");
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
