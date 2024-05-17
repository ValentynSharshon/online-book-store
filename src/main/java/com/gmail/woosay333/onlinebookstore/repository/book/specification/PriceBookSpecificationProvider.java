package com.gmail.woosay333.onlinebookstore.repository.book.specification;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSearchParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceBookSpecificationProvider extends AbstractBookSpecificationProvider<Book> {
    private static final String SEARCH_PARAMETER = BookSearchParameter.PRICE.getName();
    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 1;

    public PriceBookSpecificationProvider() {
        super(SEARCH_PARAMETER);
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(SEARCH_PARAMETER),
                        params[MIN_PRICE], params[MAX_PRICE]);
    }
}
