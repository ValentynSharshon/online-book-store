package com.gmail.woosay333.onlinebookstore.service;

import com.gmail.woosay333.onlinebookstore.dto.BookDto;
import com.gmail.woosay333.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    BookDto findById(Long id);

    List<BookDto> findAll();

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    void delete(Long id);
}
