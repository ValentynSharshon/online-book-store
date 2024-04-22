package com.gmail.woosay333.onlinebookstore.repository;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
