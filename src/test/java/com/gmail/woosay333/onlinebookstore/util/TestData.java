package com.gmail.woosay333.onlinebookstore.util;

import java.math.BigDecimal;
import java.util.Set;

public class TestData {
    public static final String VALID_BOOK_TITLE_HOBBIT = "The Hobbit";
    public static final String VALID_BOOK_AUTHOR_TOLKIEN = "J.R.R. Tolkien";
    public static final String VALID_BOOK_ISBN_HOBBIT = "9780547928227";
    public static final String INVALID_BOOK_ISBN = "9780547928123";
    public static final Set<Long> VALID_BOOK_CATEGORY_IDS_HOBBIT = Set.of(2L);
    public static final BigDecimal VALID_BOOK_PRICE_HOBBIT = BigDecimal.valueOf(14.99);
    public static final Long VALID_BOOK_ID = 1L;
    public static final Long INVALID_BOOK_ID = 10L;

    public static final String DELETE_VALUES_SQL =
            "classpath:db/script/delete-values.sql";
    public static final String INSERT_BOOKS_SQL =
            "classpath:db/script/insert-values-into-books.sql";
    public static final String INSERT_CATEGORIES_SQL =
            "classpath:db/script/insert-values-into-categories.sql";
    public static final String INSERT_BOOKS_CATEGORIES_SQL =
            "classpath:db/script/insert-values-into-books-categories.sql";
}
