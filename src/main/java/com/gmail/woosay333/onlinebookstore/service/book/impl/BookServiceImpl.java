package com.gmail.woosay333.onlinebookstore.service.book.impl;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookSearchParameters;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.exception.UniqueIsbnException;
import com.gmail.woosay333.onlinebookstore.mapper.BookMapper;
import com.gmail.woosay333.onlinebookstore.repository.book.BookRepository;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSpecificationBuilder;
import com.gmail.woosay333.onlinebookstore.service.book.BookService;
import com.gmail.woosay333.onlinebookstore.service.category.CategoryService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryService categoryService;

    @Transactional
    @Override
    public BookResponseDto createNewBook(BookRequestDto bookRequestDto) {
        if (bookRepository.findByIsbn(bookRequestDto.isbn()).isPresent()) {
            throw new UniqueIsbnException(
                    String.format("Book ISBN: %s already exist",
                            bookRequestDto.isbn()));
        }
        validateCategories(bookRequestDto);
        Book book = bookMapper.toModel(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAllBooks(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        return bookMapper.toDto(getBook(id));
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findByIdWithCategories(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Book with id: %d not found",
                                id)));
    }

    @Override
    public List<BookResponseDto> getBooksByParameters(
            BookSearchParameters bookSearchParameters, Pageable pageable) {
        Specification<Book> bookSpecification =
                bookSpecificationBuilder.build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        validateIsbnUniqueness(id, bookRequestDto);
        Book book = bookMapper.toModel(bookRequestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("Can't find book to delete by id: %d",
                            id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategories_Id(id, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private void validateIsbnUniqueness(Long id, BookRequestDto bookRequestDto) {
        List<Book> allByIdOrIsbn = bookRepository.findAllByIdOrIsbn(id, bookRequestDto.isbn());
        if (allByIdOrIsbn.size() > 1) {
            throw new UniqueIsbnException(
                    String.format("Book with ISBN: %s already exist",
                            bookRequestDto.isbn()));
        }
        if (allByIdOrIsbn.isEmpty() || !allByIdOrIsbn.getFirst().getId().equals(id)) {
            throw new EntityNotFoundException(
                    String.format("Can't find book to update by id: %d",
                            id));
        }
        validateCategories(bookRequestDto);
    }

    private void validateCategories(BookRequestDto bookRequestDto) {
        Set<Long> categoryIds = bookRequestDto.categoryIds();
        Set<Long> categoryIdsFromDb = categoryService.getAllExistedCategoryIdsFromIds(categoryIds);
        if (categoryIdsFromDb.size() < categoryIds.size()) {
            List<Long> notExistedIds = categoryIds.stream()
                    .filter(id -> !categoryIdsFromDb.contains(id))
                    .toList();
            throw new EntityNotFoundException(
                    String.format("Can't find categories with ids: %s",
                            notExistedIds));
        }
    }
}
