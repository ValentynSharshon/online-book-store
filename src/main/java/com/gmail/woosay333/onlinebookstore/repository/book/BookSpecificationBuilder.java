package com.gmail.woosay333.onlinebookstore.repository.book;

import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationBuilder;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private final SpecificationProviderManager<Book> productSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParams) {
        Specification<Book> spec = Specification.where(null);
        if (isParameterPresent(searchParams.provider())) {
            spec = addSpecification(spec, "provider", searchParams.provider());
        }
        if (isParameterPresent(searchParams.name())) {
            spec = addSpecification(spec, "name", searchParams.name());
        }
        return spec;
    }

    private Specification<Book> addSpecification(
            Specification<Book> specification,
            String key,
            String[] params
    ) {
        return specification.and(
                productSpecificationProviderManager
                .getSpecificationProvider(key)
                .getSpecification(params)
        );
    }

    private boolean isParameterPresent(String[] param) {
        return param != null && param.length > 0;
    }
}
