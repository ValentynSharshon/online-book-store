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
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        spec = buildSpecification(spec,
                bookSearchParametersDto.authors(), AUTHOR);
        spec = buildSpecification(spec,
                bookSearchParametersDto.titles(), TITLE);
        return spec;
    }

    private Specification<Book> buildSpecification(
            Specification<Book> spec,
            String[] parameters, String key
    ) {
        if (isNullOrEmpty(parameters)) {
            return spec;
        }
        return spec.and(bookSpecificationProviderManager
                .getSpecificationProvider(key)
                .getSpecification(parameters));
    }

    public static boolean isNullOrEmpty(String[] array) {
        return array == null || array.length == 0;
    }
}
