package com.gmail.woosay333.onlinebookstore.repository.category;

import static com.gmail.woosay333.onlinebookstore.util.TestData.DELETE_VALUES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_ID_1L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_ID_2L;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gmail.woosay333.onlinebookstore.entity.Category;
import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    @SneakyThrows
    public void beforeEach(@Autowired DataSource dataSource) {
        afterEach(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource(INSERT_CATEGORIES_SQL));
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
            Find all categories by IDs, return set of categories
            """)
    void findAllByIdIn_SetOfExistingCategoriesIds_ReturnSetOfCategories() {
        Set<Long> categoryIds = Set.of(VALID_CATEGORY_ID_1L, VALID_CATEGORY_ID_2L);
        List<Long> expected = List.of(VALID_CATEGORY_ID_1L, VALID_CATEGORY_ID_2L);

        Set<Category> actual = categoryRepository.findAllByIdIn(categoryIds);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.stream()
                .map(Category::getId)
                .collect(Collectors.toSet())
                .containsAll(expected));
    }

    @Test
    @DisplayName("""
            Find all categories by IDs when some given IDs are invalid,
            return set of existing categories IDs
            """)
    void findAllByIdIn_SetOfExistingAndNonExistingCategoriesIds_ReturnSetOfCategories() {
        Set<Long> categoryIds = Set.of(VALID_CATEGORY_ID_1L, VALID_CATEGORY_ID_2L, 3L, 4L, 5L, 6L);
        List<Long> expected = List.of(VALID_CATEGORY_ID_1L, VALID_CATEGORY_ID_2L);

        Set<Category> actual = categoryRepository.findAllByIdIn(categoryIds);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.stream()
                .map(Category::getId)
                .collect(Collectors.toSet())
                .containsAll(expected));
    }
}
