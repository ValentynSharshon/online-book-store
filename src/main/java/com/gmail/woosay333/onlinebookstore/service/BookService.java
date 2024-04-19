package com.gmail.woosay333.onlinebookstore.service;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
