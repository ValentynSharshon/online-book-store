package com.gmail.woosay333.onlinebookstore.util;

import java.math.BigDecimal;
import java.util.Set;

public class TestData {
    public static final String VALID_BOOK_TITLE_HOBBIT = "The Hobbit";
    public static final String VALID_BOOK_AUTHOR_TOLKIEN = "J.R.R. Tolkien";
    public static final String VALID_BOOK_ISBN_HOBBIT = "9780547928227";
    public static final String INVALID_BOOK_ISBN = "9780547928123";
    public static final String EXIST_BOOK_ISBN = "9780439064873";
    public static final Set<Long> VALID_BOOK_CATEGORY_IDS_HOBBIT = Set.of(2L);
    public static final BigDecimal VALID_BOOK_PRICE_HOBBIT = BigDecimal.valueOf(14.99);
    public static final Long VALID_BOOK_ID = 1L;
    public static final Long INVALID_BOOK_ID = 10L;

    public static final String DELETE_VALUES_SQL =
            "db/script/delete-values.sql";
    public static final String INSERT_BOOKS_SQL =
            "db/script/insert-values-into-books.sql";
    public static final String INSERT_CATEGORIES_SQL =
            "db/script/insert-values-into-categories.sql";
    public static final String INSERT_BOOKS_CATEGORIES_SQL =
            "db/script/insert-values-into-books-categories.sql";

    public static final String VALID_BOOK_TITLE_HARRY_POTTER =
            "Harry Potter and the Order of the Phoenix";
    public static final String VALID_BOOK_AUTHOR_ROWLING = "J.K. Rowling";
    public static final String VALID_BOOK_ISBN_HARRY_POTTER = "9780439358071";
    public static final Set<Long> VALID_BOOK_CATEGORY_IDS_HARRY_POTTER = Set.of(2L);
    public static final BigDecimal VALID_BOOK_PRICE_HARRY_POTTER = BigDecimal.valueOf(12.99);
}
