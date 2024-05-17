package com.gmail.woosay333.onlinebookstore.repository.book.specification;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSearchParameter;
import org.springframework.stereotype.Component;

@Component
public class IsbnBookSpecificationProvider extends AbstractBookSpecificationProvider<Book> {
    private IsbnBookSpecificationProvider() {
        super(BookSearchParameter.ISBN.getName());
    }
}
