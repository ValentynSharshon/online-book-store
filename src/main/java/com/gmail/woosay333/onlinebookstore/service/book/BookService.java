package com.gmail.woosay333.onlinebookstore.service.book;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto createNewBook(BookRequestDto bookRequestDto);

    List<BookResponseDto> getAllBooks(Pageable pageable);

    BookResponseDto getBookById(Long id);

    List<BookResponseDto> getBooksByParameters(BookSearchParameters searchParameters,
                                               Pageable pageable);

    BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto);

    void deleteBook(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);

    Book getBook(Long id);
}
