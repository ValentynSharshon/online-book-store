package com.gmail.woosay333.onlinebookstore.service.book.impl;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.exception.BookIsbnAlreadyExistsException;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.BookMapper;
import com.gmail.woosay333.onlinebookstore.repository.book.BookRepository;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSpecificationBuilder;
import com.gmail.woosay333.onlinebookstore.service.book.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto create(BookRequestDto bookRequestDto) {
        bookIsbnAlreadyExistsCheck(bookRequestDto.getIsbn());
        Book book = bookMapper.toModel(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findByIdWithCategories(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Can`t find a book with id: %d", id)
                ));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAllWithCategories(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, BookRequestDto bookRequestDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Can`t find Book with id: %d", id));
        }
        bookIsbnAlreadyExistsCheck(bookRequestDto.getIsbn());
        Book book = bookMapper.toModel(bookRequestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(
            BookSearchParameters bookSearchParameters,
            Pageable pageable
    ) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findByCategoriesId(id, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .toList();
    }

    private void bookIsbnAlreadyExistsCheck(String isbn) {
        boolean isBookExists = bookRepository.existsBookByIsbn(isbn);
        if (isBookExists) {
            throw new BookIsbnAlreadyExistsException(
                    String.format("Book with isbn: %s already exists", isbn)
            );
        }
    }
}
