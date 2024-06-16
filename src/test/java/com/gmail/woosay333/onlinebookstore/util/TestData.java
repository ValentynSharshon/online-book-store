package com.gmail.woosay333.onlinebookstore.util;

import java.math.BigDecimal;
import java.util.Set;

public class TestData {
    public static final String VALID_BOOK_TITLE_HOBBIT =
            "The Hobbit";
    public static final String VALID_BOOK_AUTHOR_TOLKIEN =
            "J.R.R. Tolkien";
    public static final String VALID_BOOK_ISBN_HOBBIT =
            "9780547928227";
    public static final String INVALID_BOOK_ISBN =
            "9780547928123";
    public static final String EXIST_BOOK_ISBN =
            "9780439064873";
    public static final Set<Long> VALID_BOOK_CATEGORY_IDS_HOBBIT =
            Set.of(1L);
    public static final BigDecimal VALID_BOOK_PRICE_HOBBIT =
            BigDecimal.valueOf(14.99);
    public static final Long VALID_BOOK_ID_HOBBIT =
            2L;
    public static final Long INVALID_BOOK_ID_10L =
            10L;

    public static final String DELETE_VALUES_SQL =
            "db/script/delete-values.sql";
    public static final String INSERT_BOOKS_SQL =
            "db/script/insert-values-into-books.sql";
    public static final String INSERT_CATEGORIES_SQL =
            "db/script/insert-values-into-categories.sql";
    public static final String INSERT_BOOKS_CATEGORIES_SQL =
            "db/script/insert-values-into-books-categories.sql";

    public static final String CREATE_BOOK_TITLE_LORD_OF_THE_RINGS =
            "The Lord of the Rings";
    public static final String CREATE_BOOK_AUTHOR_TOLKIEN =
            "Tolkien";
    public static final String CREATE_BOOK_ISBN_LORD_OF_THE_RINGS =
            "9780544003415";
    public static final BigDecimal CREATE_BOOK_PRICE_LORD_OF_THE_RINGS =
            BigDecimal.valueOf(25.00);
    public static final Set<Long> CREATE_BOOK_CATEGORIES_LORD_OF_THE_RINGS =
            Set.of(1L);
    public static final String UPDATE_BOOK_TITLE_TEST_TITLE =
            "Updated title";
    public static final String UPDATE_BOOK_AUTHOR_TEST_AUTHOR =
            "Updated author";

    public static final String VALID_BOOK_TITLE_HARRY_POTTER_CHAMBER_OF_SECRETS =
            "Harry Potter and the Chamber of Secrets";
    public static final String VALID_BOOK_AUTHOR_ROWLING =
            "J.K. Rowling";
    public static final String VALID_BOOK_ISBN_HARRY_POTTER_CHAMBER_OF_SECRETS =
            "9780439064873";
    public static final Set<Long> VALID_BOOK_CATEGORY_IDS_HARRY_POTTER_CHAMBER_OF_SECRETS =
            Set.of(1L);
    public static final BigDecimal VALID_BOOK_PRICE_HARRY_POTTER_CHAMBER_OF_SECRETS =
            BigDecimal.valueOf(10.99);
    public static final Long VALID_BOOK_ID_HARRY_POTTER_CHAMBER_OF_SECRETS =
            1L;

    public static final Long VALID_CATEGORY_ID_1L =
            1L;
    public static final Long VALID_CATEGORY_ID_2L =
            2L;
    public static final Long INVALID_CATEGORY_ID_5L =
            5L;
    public static final String VALID_CATEGORY_NAME_FANTASY =
            "Fantasy";
    public static final String VALID_CATEGORY_NAME_NOVEL =
            "Novel";
    public static final String VALID_CATEGORY_DESCRIPTION_FANTASY =
            "Fantasy description";
    public static final String VALID_CATEGORY_DESCRIPTION_NOVEL =
            "Novel description";
    public static final String UPDATE_CATEGORY_TITLE =
            "Updated title";
    public static final String UPDATE_CATEGORY_DESCRIPTION =
            "Updated description";
}
