package com.gmail.woosay333.onlinebookstore.service;

import com.gmail.woosay333.onlinebookstore.dto.BookResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto bookRequestDto);

    BookResponseDto findById(Long id);

    List<BookResponseDto> findAll();
}
