package com.exemple.socialmedia.domain.Post.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.UUID;

@Getter
@Setter
public class PostFilters {
    private String search;
    private UUID userId;
    private String orderBy = "createdAt";
    private Sort.Direction direction = Sort.Direction.DESC;
}
