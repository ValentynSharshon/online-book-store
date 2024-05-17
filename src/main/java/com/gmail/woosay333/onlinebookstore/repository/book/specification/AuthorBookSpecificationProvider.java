package com.gmail.woosay333.onlinebookstore.repository.book.specification;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSearchParameter;
import org.springframework.stereotype.Component;

@Component
public class AuthorBookSpecificationProvider extends AbstractBookSpecificationProvider<Book> {
    private AuthorBookSpecificationProvider() {
        super(BookSearchParameter.AUTHOR.getName());
    }
}
