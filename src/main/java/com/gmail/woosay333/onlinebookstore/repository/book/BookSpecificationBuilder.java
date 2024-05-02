package com.gmail.woosay333.onlinebookstore.repository.book;

import com.gmail.woosay333.onlinebookstore.dto.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationBuilder;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        spec = buildSpecification(spec,
                bookSearchParametersDto.authors(),
                "author");
        spec = buildSpecification(spec,
                bookSearchParametersDto.titles(), "title");
        return spec;
    }

    private Specification<Book> buildSpecification(
            Specification<Book> specification,
            String[] params, String key
    ) {
        if (isSearchParamsNullOrEmpty(params)) {
            return specification;
        }
        return specification.and(bookSpecificationProviderManager
                .getSpecificationProvider(key)
                .getSpecification(params));
    }

    private boolean isSearchParamsNullOrEmpty(String[] params) {
        return params == null || params.length == 0;
    }
}
