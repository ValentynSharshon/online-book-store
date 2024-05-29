package com.gmail.woosay333.onlinebookstore.service.book;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.dto.book.CreateBookRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> getAll(Pageable pageable);

    BookDto getById(Long id);

    List<BookDto> getByParameters(BookSearchParameters searchParameters,
                                  Pageable pageable);

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    void delete(Long id);

    List<BookDtoWithoutCategoryIds> getByCategoryId(Long id, Pageable pageable);

    Book getBook(Long id);
}
