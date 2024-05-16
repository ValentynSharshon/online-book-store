package com.gmail.woosay333.onlinebookstore.repository.book.specification;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProviderSpecificationProvider implements SpecificationProvider<Book> {
    private static final String KEY_PROVIDER = "provider";

    @Override
    public String getKey() {
        return KEY_PROVIDER;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("provider").in(Arrays.stream(params).toArray());
    }
}