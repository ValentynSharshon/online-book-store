package com.gmail.woosay333.onlinebookstore.repository.book;

import static com.gmail.woosay333.onlinebookstore.util.TestData.DELETE_VALUES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INVALID_BOOK_ID_10L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INVALID_BOOK_ISBN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ID_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ISBN_HOBBIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    @SneakyThrows
    public void beforeEach(@Autowired DataSource dataSource) {
        afterEach(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource(INSERT_BOOKS_SQL));
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource(INSERT_CATEGORIES_SQL));
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource(INSERT_BOOKS_CATEGORIES_SQL));
        }
    }

    @AfterEach
    @SneakyThrows
    public void afterEach(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(DELETE_VALUES_SQL));
        }
    }

    @Test
    @DisplayName("""
            Find book by existing ISBN, return optional of book by ISBN
            """)
    void findByIsbn_ExistingBookIsbn_ReturnOptionalOfBook() {
        Optional<Book> actual = bookRepository.findByIsbn(VALID_BOOK_ISBN_HOBBIT);

        assertTrue(actual.isPresent(), "Optional of book by existing ISBN should be present");
    }

    @Test
    @DisplayName("""
            Find book by non existing ISBN, return empty optional of book by ISBN
            """)
    void findByIsbn_NonExistingBookIsbn_ReturnEmptyOptionalOfBook() {
        Optional<Book> actual = bookRepository.findByIsbn(INVALID_BOOK_ISBN);

        assertTrue(actual.isEmpty(), "Optional of book by non-existing ISBN should be empty");
    }

    @Test
    @DisplayName("""
            Find book with categories by existing ID,
            return optional of book with set of categories
            """)
    void findByIdWithCategories_FindingBookByExistingId_ReturnOptionalOfBook() {
        Optional<Book> actual = bookRepository.findByIdWithCategories(VALID_BOOK_ID_HOBBIT);

        assertTrue(actual.isPresent(), "Optional of book by existing ID should be present");
        assertNotNull(actual.get().getCategories());
    }

    @Test
    @DisplayName("""
            Find book with categories by non existing ID, return empty optional
            """)
    void findByIdWithCategories_FindingBookByNonExistingId_ReturnEmptyOptional() {
        Optional<Book> actual = bookRepository.findByIdWithCategories(INVALID_BOOK_ID_10L);

        assertTrue(actual.isEmpty(), "Optional of book by non-existing ID should be empty");
    }

    @Test
    @DisplayName("""
            Find all books with categories by params, return page of books
            """)
    void findAllByParams_ReturnAllBooksByParams() {
        Specification<Book> specification = Specification.where(null);
        Pageable pageable = Pageable.unpaged();

        Page<Book> actual = bookRepository.findAll(specification, pageable);
        int expectedSize = 3;

        assertEquals(expectedSize, actual.getSize());
        actual.stream()
                .map(Book::getCategories)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    @DisplayName("""
            Find all books with categories, returns list of books
            """)
    void findAllBooks_GetAllBooks_ReturnAllBooks() {
        Pageable pageable = Pageable.unpaged();

        List<Book> actual = bookRepository.findAllBooks(pageable);
        int expectedSize = 3;

        assertEquals(expectedSize, actual.size());
        actual.stream()
                .map(Book::getCategories)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    @DisplayName("""
            Find all books witch belongs to category ID, returns list of books
            """)
    void findAllByCategoryId_ExistCategoryId_ReturnAllBooksByCategoryId() {
        Pageable pageable = Pageable.unpaged();
        Long fantasyId = 1L;
        Long novelId = 2L;

        List<Book> actualFantasy = bookRepository.findAllByCategories_Id(fantasyId, pageable);
        List<Book> actualNovel = bookRepository.findAllByCategories_Id(novelId, pageable);

        assertNotNull(actualFantasy);
        assertNotNull(actualNovel);

        int expectedSizeFantasy = 2;
        int expectedSizeNovel = 1;

        assertEquals(expectedSizeFantasy, actualFantasy.size(), "Size should be equals");
        assertEquals(expectedSizeNovel, actualNovel.size(), "Size should be equals");
    }

    @Test
    @DisplayName("""
            Find all books by ID and ISBN, returns list of books
            """)
    void findAllByIdOrIsbn_GetAllBooksByIdAndIsbn_ReturnsAllBooksByIdAndIsbn() {
        final List<Book> actualValidIdValidIsbn = bookRepository
                .findAllByIdOrIsbn(VALID_BOOK_ID_HOBBIT, VALID_BOOK_ISBN_HOBBIT);
        final List<Book> actualInvalidIdValidIsbn = bookRepository
                .findAllByIdOrIsbn(INVALID_BOOK_ID_10L, VALID_BOOK_ISBN_HOBBIT);
        final List<Book> actualValidIdInvalidIsbn = bookRepository
                .findAllByIdOrIsbn(VALID_BOOK_ID_HOBBIT, INVALID_BOOK_ISBN);
        final List<Book> actualInvalidIdInvalidIsbn = bookRepository
                .findAllByIdOrIsbn(INVALID_BOOK_ID_10L, INVALID_BOOK_ISBN);

        final int expectedBookListSizeZero = 0;
        final int expectedBookListSizeOne = 1;

        assertEquals(expectedBookListSizeOne, actualValidIdValidIsbn.size());
        assertEquals(expectedBookListSizeOne, actualInvalidIdValidIsbn.size());
        assertEquals(expectedBookListSizeOne, actualValidIdInvalidIsbn.size());
        assertEquals(expectedBookListSizeZero, actualInvalidIdInvalidIsbn.size());
    }
}
