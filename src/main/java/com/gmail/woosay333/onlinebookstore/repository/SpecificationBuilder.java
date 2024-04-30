package com.gmail.woosay333.onlinebookstore.repository;

import com.gmail.woosay333.onlinebookstore.dto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters bookSearchParameters);
}
