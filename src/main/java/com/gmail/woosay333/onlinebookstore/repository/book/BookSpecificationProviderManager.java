package com.gmail.woosay333.onlinebookstore.repository.book;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationProvider;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationProviderManager;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String bookSearchParameter) {
        return bookSpecificationProviders.stream()
                .filter(provider -> provider.getSearchParameter()
                        .equals(bookSearchParameter))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Can't find correct specification provider or parameter %s",
                                bookSearchParameter)));
    }
}
