package com.gmail.woosay333.onlinebookstore.repository.book;

import static com.gmail.woosay333.onlinebookstore.util.TestData.DELETE_VALUES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INVALID_BOOK_ID;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INVALID_BOOK_ISBN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ID;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ISBN_HOBBIT;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gmail.woosay333.onlinebookstore.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find book by existing ISBN, return optional of book by ISBN")
    @Sql(scripts = {DELETE_VALUES_SQL, INSERT_BOOKS_SQL, INSERT_CATEGORIES_SQL, INSERT_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByIsbn_ExistingBookIsbn_ReturnsOptionalOfBook() {
        Optional<Book> actual = bookRepository.findByIsbn(VALID_BOOK_ISBN_HOBBIT);
        assertTrue(actual.isPresent(), "Optional of book by existing ISBN should be present");
    }

    @Test
    @DisplayName("Find book by non existing ISBN, return empty optional of book by ISBN")
    @Sql(scripts = {DELETE_VALUES_SQL, INSERT_BOOKS_SQL, INSERT_CATEGORIES_SQL, INSERT_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByIsbn_NonExistingBookIsbn_ReturnsEmptyOptionalOfBook() {
        Optional<Book> actual = bookRepository.findByIsbn(INVALID_BOOK_ISBN);
        assertTrue(actual.isEmpty(), "Optional of book by non-existing ISBN should be empty");
    }

    @Test
    @DisplayName("Find book with categories by existing ID, return optional of book with set of categories")
    @Sql(scripts = {DELETE_VALUES_SQL, INSERT_BOOKS_SQL, INSERT_CATEGORIES_SQL, INSERT_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByIdWithCategories_FindingBookByExistingId_ReturnsOptionalOfBook() {
        Optional<Book> actual = bookRepository.findByIdWithCategories(VALID_BOOK_ID);
        assertTrue(actual.isPresent(), "Optional of book by existing ID should be present");
        assertNotNull(actual.get().getCategories());
    }

    @Test
    @DisplayName("Find book with categories by non existing ID, return empty optional")
    @Sql(scripts = {DELETE_VALUES_SQL, INSERT_BOOKS_SQL, INSERT_CATEGORIES_SQL, INSERT_BOOKS_CATEGORIES_SQL},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByIdWithCategories_FindingBookByNonExistingId_ReturnsEmptyOptional() {
        Optional<Book> actual = bookRepository.findByIdWithCategories(INVALID_BOOK_ID);
        assertTrue(actual.isEmpty(), "Optional of book by non-existing ID should be empty");
    }
}