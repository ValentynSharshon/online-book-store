package com.gmail.woosay333.onlinebookstore.service.book.impl;

import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_AUTHOR_TOLKIEN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_CATEGORY_IDS_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ID;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ISBN_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_PRICE_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_TITLE_HOBBIT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.Category;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.exception.UniqueIsbnException;
import com.gmail.woosay333.onlinebookstore.mapper.BookMapper;
import com.gmail.woosay333.onlinebookstore.repository.book.BookRepository;
import com.gmail.woosay333.onlinebookstore.repository.book.BookSpecificationBuilder;
import com.gmail.woosay333.onlinebookstore.service.category.CategoryService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(bookRepository, categoryService, bookMapper);
    }

    @Test
    @DisplayName("Save a new book, returns BookResponseDto")
    void createNewBook_WithUniqueIsbnAndExistCategories_ReturnsBookResponseDto() {
        BookRequestDto requestDto = createBookRequestDto();
        Book book = createBook();

        mockForCreateBookMethod(requestDto, VALID_BOOK_CATEGORY_IDS_HOBBIT);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        mockBookMapperMethods(book);

        BookResponseDto actual = bookService.createNewBook(requestDto);

        assertNotNull(actual);
        BookResponseDto expected = getBookResponseDto(book);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("Save a new book with existing ISBN, trows exception")
    void createNewBook_WithNonUniqueIsbn_ThrowsException() {
        BookRequestDto requestDto = createBookRequestDto();
        Book book = createBook();

        when(bookRepository.findByIsbn(requestDto.isbn())).thenReturn(Optional.of(book));

        Exception actual = assertThrows(UniqueIsbnException.class, () -> bookService.createNewBook(requestDto));
        assertEquals(String.format("Book ISBN: %s already exist", book.getIsbn()), actual.getMessage());
    }

    @Test
    @DisplayName("Save a new book with non-existing categories, trows exception")
    void createNewBook_WithNonExistingCategories_ThrowsException() {
        BookRequestDto requestDto = createBookRequestDto();
        Set<Long> existedCategoriesIds = Set.of();

        mockForCreateBookMethod(requestDto, existedCategoriesIds);

        Exception actual = assertThrows(EntityNotFoundException.class, () -> bookService.createNewBook(requestDto));
        assertEquals(String.format("Can't find categories with ids: %s", VALID_BOOK_CATEGORY_IDS_HOBBIT), actual.getMessage());
    }

    @Test
    @DisplayName("Get all books from the repository, returns list of BookResponseDto")
    void getAllBooks_ReturnsAllBooksFromDb() {
        List<Book> expected = List.of(createBook(), createBook());

        when(bookRepository.findAllBooks(any(Pageable.class))).thenReturn(expected);
        when(bookMapper.toDto(any(Book.class))).thenReturn(getBookResponseDto(createBook()));

        List<BookResponseDto> actual = bookService.getAllBooks(Pageable.unpaged());
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("Get existing book from the repository, returns BookResponseDto")
    void getBookById_ExistingBookId_ReturnsBookResponseDto() {
        Book bookFromRepository = createBook();
        bookFromRepository.setId(VALID_BOOK_ID);

        when(bookRepository.findByIdWithCategories(VALID_BOOK_ID))
                .thenReturn(Optional.of(bookFromRepository));
        when(bookMapper.toDto(bookFromRepository))
                .thenReturn(getBookResponseDto(bookFromRepository));

        BookResponseDto actual = bookService.getBookById(VALID_BOOK_ID);
        BookResponseDto expected = getBookResponseDto(bookFromRepository);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("Get non-existing book from repository, throws exception")
    void getBookById_NonExistingBookId_ThrowsException() {
        when(bookRepository.findByIdWithCategories(VALID_BOOK_ID)).thenReturn(Optional.empty());

        Exception actual = assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(VALID_BOOK_ID));
        assertEquals(String.format("Book with id: %d not found", VALID_BOOK_ID), actual.getMessage());
    }

    @Test
    @DisplayName("Update existing book and unique ISBN, returns BookResponseDto")
    void updateBook_ExistingBook_ReturnsBookResponseDto() {
        Book existingBook = createBook();
        existingBook.setId(VALID_BOOK_ID);
        existingBook.setPrice(BigDecimal.TEN);

        mockForUpdateBookMethod(existingBook, VALID_BOOK_CATEGORY_IDS_HOBBIT);
        when(bookRepository.save(any(Book.class))).thenReturn(createBook());
        mockBookMapperMethods(existingBook);

        BookResponseDto actual = bookService.updateBook(VALID_BOOK_ID, createBookRequestDto());
        assertTrue(EqualsBuilder.reflectionEquals(getBookResponseDto(existingBook), actual));
    }

    @Test
    @DisplayName("Update non-existing book, throws exception")
    void updateBook_NonExistingBook_ThrowsException() {
        BookRequestDto requestDto = createBookRequestDto();

        when(bookRepository.findAllByIdOrIsbn(VALID_BOOK_ID, VALID_BOOK_ISBN_HOBBIT))
                .thenReturn(List.of());

        Exception actual = assertThrows(EntityNotFoundException.class,
                () -> bookService.updateBook(VALID_BOOK_ID, requestDto));
        assertEquals(String.format("Can't find book to update by id: %d", VALID_BOOK_ID), actual.getMessage());
    }

    @Test
    @DisplayName("Update with non unique ISBN, throws exception")
    void updateBook_NonUniqueBookIsbn_ThrowsException() {
        BookRequestDto requestDto = createBookRequestDto();
        Book existingBook = createBook();
        existingBook.setId(VALID_BOOK_ID);
        Book bookWithSameIsbn = createBook();

        when(bookRepository.findAllByIdOrIsbn(VALID_BOOK_ID, VALID_BOOK_ISBN_HOBBIT))
                .thenReturn(List.of(existingBook, bookWithSameIsbn));

        Exception actual = assertThrows(UniqueIsbnException.class,
                () -> bookService.updateBook(VALID_BOOK_ID, requestDto));
        assertEquals(String.format("Book with ISBN: %s already exist", VALID_BOOK_ISBN_HOBBIT), actual.getMessage());
    }

    @Test
    @DisplayName("Update with invalid categories IDs, throws exception")
    void updateBook_NonExistingBookCategories_ThrowsException() {
        BookRequestDto requestDto = createBookRequestDto();
        Book existingBook = createBook();
        existingBook.setId(VALID_BOOK_ID);
        Set<Long> existedCategoriesIds = Set.of();

        mockForUpdateBookMethod(existingBook, existedCategoriesIds);

        Exception actual = assertThrows(EntityNotFoundException.class,
                        () -> bookService.updateBook(VALID_BOOK_ID, requestDto));
        assertEquals(String.format("Can't find categories with ids: %s", VALID_BOOK_CATEGORY_IDS_HOBBIT), actual.getMessage());
    }

    @Test
    @DisplayName("Delete existing book, returns void")
    void deleteBook_ExistingBook_SuccessfullyDeleted() {
        when(bookRepository.findById(VALID_BOOK_ID))
                .thenReturn(Optional.of(new Book()));
        doNothing().when(bookRepository).deleteById(VALID_BOOK_ID);

        assertDoesNotThrow(() -> bookService.deleteBook(VALID_BOOK_ID));
    }

    @Test
    @DisplayName("Delete non-existing book, throws exception")
    void delete_NonExistingBook_ThrowsException() {
        when(bookRepository.findById(VALID_BOOK_ID))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.deleteBook(VALID_BOOK_ID));

        assertEquals(String.format("Can't find book to delete by id: %d", VALID_BOOK_ID), exception.getMessage());
    }

    @Test
    @DisplayName("Get all books by category, returns list of BookDtoWithoutCategoryIds")
    void getByCategoryId_FindAllBooksByCategoryId_ReturnsBooksForExistingCategory() {
        Pageable pageable = Pageable.unpaged();
        List<Book> expected = List.of(createBook(), createBook());

        when(bookRepository.findAllByCategories_Id(VALID_BOOK_ID, pageable))
                .thenReturn(expected);
        when(bookMapper.toDtoWithoutCategories(any(Book.class))).thenReturn(
                new BookDtoWithoutCategoryIds(
                        VALID_BOOK_ID,
                        VALID_BOOK_TITLE_HOBBIT,
                        VALID_BOOK_AUTHOR_TOLKIEN,
                        VALID_BOOK_ISBN_HOBBIT,
                        VALID_BOOK_PRICE_HOBBIT,
                        null,
                        null)
        );

        List<BookDtoWithoutCategoryIds> actual = bookService.getBooksByCategoryId(VALID_BOOK_ID, pageable);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("Get by list of books by parameter, returns filtered list of BookResponseDto")
    void getBooksByParameters_listOfSearchBookParameters_ReturnsFilteredBookResponseDto() {
        Pageable pageable = Pageable.unpaged();
        Specification<Book> specification = Specification.where(null);
        Page<Book> booksFromRepository = new PageImpl<>(List.of(createBook(), createBook()));

        when(bookSpecificationBuilder.build(any()))
                .thenReturn(specification);
        when(bookRepository.findAll(specification, pageable))
                .thenReturn(booksFromRepository);
        when(bookMapper.toDto(any(Book.class)))
                .thenReturn(getBookResponseDto(createBook()));

        List<BookResponseDto> actual = bookService.getBooksByParameters(any(), pageable);
        assertEquals(booksFromRepository.getSize(), actual.size());
    }

    private static BookRequestDto createBookRequestDto() {
        return BookRequestDto.builder()
                .title(VALID_BOOK_TITLE_HOBBIT)
                .author(VALID_BOOK_AUTHOR_TOLKIEN)
                .isbn(VALID_BOOK_ISBN_HOBBIT)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HOBBIT)
                .price(VALID_BOOK_PRICE_HOBBIT)
                .build();
    }

    private static Book createBook() {
        Book book = new Book();
        book.setTitle(VALID_BOOK_TITLE_HOBBIT);
        book.setAuthor(VALID_BOOK_AUTHOR_TOLKIEN);
        book.setIsbn(VALID_BOOK_ISBN_HOBBIT);
        book.setCategories(VALID_BOOK_CATEGORY_IDS_HOBBIT.stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
        book.setPrice(VALID_BOOK_PRICE_HOBBIT);
        return book;
    }

    private void mockForCreateBookMethod(BookRequestDto requestDto, Set<Long> categoryIds) {
        when(bookRepository.findByIsbn(requestDto.isbn()))
                .thenReturn(Optional.empty());
        when(categoryService.getAllExistedCategoryIdsFromIds(VALID_BOOK_CATEGORY_IDS_HOBBIT))
                .thenReturn(categoryIds);
    }

    private void mockBookMapperMethods(Book book) {
        when(bookMapper.toModel(any(BookRequestDto.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(getBookResponseDto(book));
    }

    public static BookResponseDto getBookResponseDto(Book book) {
        return BookResponseDto.builder()
                .categoryIds(book.getCategories().stream()
                                .map(Category::getId)
                                .collect(Collectors.toSet()))
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .build();
    }

    private void mockForUpdateBookMethod(Book existingBook, Set<Long> categoryIds) {
        when(bookRepository.findAllByIdOrIsbn(VALID_BOOK_ID, VALID_BOOK_ISBN_HOBBIT))
                .thenReturn(List.of(existingBook));
        when(categoryService.getAllExistedCategoryIdsFromIds(VALID_BOOK_CATEGORY_IDS_HOBBIT))
                .thenReturn(categoryIds);
    }
}