package com.gmail.woosay333.onlinebookstore.repository.book.specification;

import com.gmail.woosay333.onlinebookstore.repository.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Getter
@RequiredArgsConstructor
public abstract class AbstractBookSpecificationProvider<T> implements SpecificationProvider<T> {
    private final String searchParameter;

    @Override
    public Specification<T> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .or(Arrays.stream(params)
                        .map(parameter -> criteriaBuilder
                                .like(criteriaBuilder
                                                .lower(root.get(searchParameter)),
                                '%' + parameter.toLowerCase() + '%')).toArray(Predicate[]::new));
    }
}
