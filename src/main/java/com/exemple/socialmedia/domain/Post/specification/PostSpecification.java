package com.exemple.socialmedia.domain.Post.specification;

import com.exemple.socialmedia.domain.Exception.HttpException;
import com.exemple.socialmedia.domain.Post.Post;
import com.exemple.socialmedia.domain.Post.filters.PostFilters;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> filterBy(PostFilters filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters.getSearch() != null) {
                Predicate contentMatch = criteriaBuilder
                        .like(root.get("content"), "%" + filters.getSearch() + "%");
                predicates.add(contentMatch);
            }
            if (filters.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user_id"), filters.getUserId()));
            }
            if (query == null)
                throw new HttpException("Something went wrong while creating posts query specification.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            query.orderBy(filters.getDirection().isAscending() ? criteriaBuilder.asc(root.get(filters.getOrderBy()))
                    : criteriaBuilder.desc(root.get(filters.getOrderBy())));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
