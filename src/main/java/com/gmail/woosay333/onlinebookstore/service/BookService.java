package com.gmail.woosay333.onlinebookstore.service;

import com.gmail.woosay333.onlinebookstore.dto.BookDto;
import com.gmail.woosay333.onlinebookstore.dto.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.BookSearchParameters;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto create(BookRequestDto bookRequestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto update(Long id, BookRequestDto bookRequestDto);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters searchParameters, Pageable pageable);
}
