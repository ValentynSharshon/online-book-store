package com.gmail.woosay333.onlinebookstore.service;

import com.gmail.woosay333.onlinebookstore.dto.BookDto;
import com.gmail.woosay333.onlinebookstore.dto.BookRequestDto;
import java.util.List;

public interface BookService {
    BookDto create(BookRequestDto bookRequestDto);

    BookDto findById(Long id);

    List<BookDto> findAll();

    BookDto update(Long id, BookRequestDto bookRequestDto);

    void delete(Long id);
}
