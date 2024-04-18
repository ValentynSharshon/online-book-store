package com.gmail.woosay333.onlinebookstore.repository;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
