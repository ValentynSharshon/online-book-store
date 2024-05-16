package com.gmail.woosay333.onlinebookstore.service.book;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto create(BookRequestDto bookRequestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto update(Long id, BookRequestDto bookRequestDto);

    void delete(Long id);

    List<BookDtoWithoutCategoryIds> search(BookSearchParameters searchParameters,
                                           Pageable pageable);

    List<BookDtoWithoutCategoryIds> getByCategoryId(Long id, Pageable pageable);
}
