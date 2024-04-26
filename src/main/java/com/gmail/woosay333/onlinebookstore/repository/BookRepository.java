package com.gmail.woosay333.onlinebookstore.repository;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
