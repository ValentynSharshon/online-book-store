package com.gmail.woosay333.onlinebookstore.controller;

import static com.gmail.woosay333.onlinebookstore.util.TestData.DELETE_VALUES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.EXIST_BOOK_ISBN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_AUTHOR_ROWLING;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_CATEGORY_IDS_HARRY_POTTER;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ISBN_HARRY_POTTER;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_PRICE_HARRY_POTTER;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_TITLE_HARRY_POTTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import java.sql.Connection;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static final String URI = "/books";
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

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
    void afterEach(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(DELETE_VALUES_SQL));
        }
    }

    @Test
    @DisplayName("""
            Create a new book, expected status 201, return BookResponseDto
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void createNewBook_ValidBookRequestDto_ReturnBookResponseDto() throws Exception {
        BookRequestDto requestDto = createBookRequestDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post(URI)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        BookResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto.class);

        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(getBookResponseDto(requestDto),
                actual,
                "id", "isbn", "categoryIds"));
        assertNotNull(actual.id());
        assertEquals(VALID_BOOK_ISBN_HARRY_POTTER, actual.isbn());
        assertTrue(requestDto.categoryIds().containsAll(actual.categoryIds()));
    }

    @Test
    @DisplayName("""
            Create new book with existing ISBN,
            expected: status - 409, response - ProblemDetail
            """)
    @WithMockUser(username = "admin", roles = {"MANAGER"})
    void createNewBook_BookRequestDtoWithExistingIsbn_ResponseConflictAndReturnsProblemDetail()
            throws Exception {
        BookRequestDto requestDto = BookRequestDto.builder()
                .title(VALID_BOOK_TITLE_HARRY_POTTER)
                .author(VALID_BOOK_AUTHOR_ROWLING)
                .isbn(EXIST_BOOK_ISBN)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HARRY_POTTER)
                .price(VALID_BOOK_PRICE_HARRY_POTTER)
                .build();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post(URI)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
        ProblemDetail actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), ProblemDetail.class);

        assertNotNull(actual);
        assertEquals("Conflict", actual.getTitle());
        assertNotNull(actual.getInstance());
        assertEquals(URI, actual.getInstance().getPath());
        assertNotNull(actual.getProperties());
        assertEquals(String.format("Book ISBN: %s already exist", EXIST_BOOK_ISBN),
                actual.getProperties().get("error"));
    }

    private static BookRequestDto createBookRequestDto() {
        return BookRequestDto.builder()
                .title(VALID_BOOK_TITLE_HARRY_POTTER)
                .author(VALID_BOOK_AUTHOR_ROWLING)
                .isbn(VALID_BOOK_ISBN_HARRY_POTTER)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HARRY_POTTER)
                .price(VALID_BOOK_PRICE_HARRY_POTTER)
                .build();
    }

    private static BookResponseDto getBookResponseDto(BookRequestDto requestDto) {
        return BookResponseDto.builder()
                .title(requestDto.title())
                .author(requestDto.author())
                .isbn(requestDto.isbn())
                .categoryIds(requestDto.categoryIds())
                .price(requestDto.price())
                .build();
    }
}
