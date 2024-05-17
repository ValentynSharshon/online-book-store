package com.gmail.woosay333.onlinebookstore.repository.book;

import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationBuilder;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationProviderManager;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        return Arrays.stream(BookSearchParameter.values())
                .map(parameter -> getSpecification(bookSearchParameters, parameter))
                .reduce(Specification.where(null), Specification::and);
    }

    private Specification<Book> getSpecification(
            BookSearchParameters bookSearchParameters,
            BookSearchParameter searchParams) {
        String[] parameters = switch (searchParams) {
            case TITLE -> bookSearchParameters.titles();
            case CATEGORY -> bookSearchParameters.categoryIds();
            case AUTHOR -> bookSearchParameters.authors();
            case ISBN -> bookSearchParameters.isbns();
            case PRICE -> {
                Long minPrice = Optional.ofNullable(
                        bookSearchParameters.minPrice()).orElse(0L);
                Long maxPrice = Optional.ofNullable(
                        bookSearchParameters.maxPrice()).orElse(Long.MAX_VALUE);
                yield new String[]{String.valueOf(minPrice), String.valueOf(maxPrice)};
            }
        };
        return isValidParameters(parameters)
                ? bookSpecificationProviderManager.getSpecificationProvider(searchParams.getName())
                .getSpecification(parameters) : null;
    }

    private boolean isValidParameters(String[] parameters) {
        return parameters != null && parameters.length > 0;
    }
}
