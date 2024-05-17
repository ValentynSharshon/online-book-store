package com.gmail.woosay333.onlinebookstore.repository.book.specification;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.Category;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSearchParameter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategoriesBookSpecificationProvider extends AbstractBookSpecificationProvider<Book> {
    private CategoriesBookSpecificationProvider() {
        super(BookSearchParameter.CATEGORY.getName());
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Book, Category> categoryJoin = root
                    .join("categories", JoinType.INNER);
            return categoryJoin.get("id")
                    .in(Arrays.stream(params)
                            .map(Long::parseLong)
                            .toArray());
        };
    }
}
