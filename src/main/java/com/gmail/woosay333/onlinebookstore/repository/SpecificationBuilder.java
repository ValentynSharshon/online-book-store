package com.gmail.woosay333.onlinebookstore.repository;

import com.gmail.woosay333.onlinebookstore.dto.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<Book> build(BookSearchParameters bookSearchParameters);
}
