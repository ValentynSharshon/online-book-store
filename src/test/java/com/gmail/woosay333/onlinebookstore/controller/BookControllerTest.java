package com.gmail.woosay333.onlinebookstore.controller;

import static com.gmail.woosay333.onlinebookstore.util.SqlScript.DELETE_VALUES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.SqlScript.INSERT_BOOKS_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.SqlScript.INSERT_BOOKS_SQL;
import static com.gmail.woosay333.onlinebookstore.util.SqlScript.INSERT_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.CREATE_BOOK_AUTHOR_TOLKIEN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.CREATE_BOOK_CATEGORIES_LORD_OF_THE_RINGS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.CREATE_BOOK_ISBN_LORD_OF_THE_RINGS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.CREATE_BOOK_PRICE_LORD_OF_THE_RINGS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.CREATE_BOOK_TITLE_LORD_OF_THE_RINGS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.EXIST_BOOK_ISBN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.UPDATE_BOOK_AUTHOR_TEST_AUTHOR;
import static com.gmail.woosay333.onlinebookstore.util.TestData.UPDATE_BOOK_TITLE_TEST_TITLE;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_AUTHOR_ROWLING;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_AUTHOR_TOLKIEN;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_CATEGORY_IDS_HARRY_POTTER_CHAMBER_OF_SECRETS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_CATEGORY_IDS_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ID_HARRY_POTTER_CHAMBER_OF_SECRETS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ID_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ISBN_HARRY_POTTER_CHAMBER_OF_SECRETS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_ISBN_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_PRICE_HARRY_POTTER_CHAMBER_OF_SECRETS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_PRICE_HOBBIT;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_TITLE_HARRY_POTTER_CHAMBER_OF_SECRETS;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_BOOK_TITLE_HOBBIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
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
        BookRequestDto requestDto = BookRequestDto.builder()
                .title(CREATE_BOOK_TITLE_LORD_OF_THE_RINGS)
                .author(CREATE_BOOK_AUTHOR_TOLKIEN)
                .categoryIds(CREATE_BOOK_CATEGORIES_LORD_OF_THE_RINGS)
                .isbn(CREATE_BOOK_ISBN_LORD_OF_THE_RINGS)
                .price(CREATE_BOOK_PRICE_LORD_OF_THE_RINGS)
                .build();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post(URI)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        BookResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto.class);

        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(BookResponseDto.builder()
                .title(requestDto.title())
                .author(requestDto.author())
                .isbn(requestDto.isbn())
                .categoryIds(requestDto.categoryIds())
                .price(requestDto.price())
                .build(),
                actual,
                "id", "isbn", "categoryIds"));
        assertNotNull(actual.id());
        assertEquals(CREATE_BOOK_ISBN_LORD_OF_THE_RINGS, actual.isbn());
        assertTrue(requestDto.categoryIds().containsAll(actual.categoryIds()));
    }

    @Test
    @DisplayName("""
            Create a new book with existing ISBN,
            expected status - 409, expected response - ProblemDetail
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void createNewBook_BookRequestDtoWithExistingIsbn_ResponseConflictAndReturnsProblemDetail()
            throws Exception {
        BookRequestDto requestDto = BookRequestDto.builder()
                .title(CREATE_BOOK_TITLE_LORD_OF_THE_RINGS)
                .author(CREATE_BOOK_AUTHOR_TOLKIEN)
                .isbn(EXIST_BOOK_ISBN)
                .categoryIds(CREATE_BOOK_CATEGORIES_LORD_OF_THE_RINGS)
                .price(CREATE_BOOK_PRICE_LORD_OF_THE_RINGS)
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

    @Test
    @DisplayName("""
            Update existing book,
            expected status - 202, expected response - BookResponseDto
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void updateBook_ValidBookRequestDto_ReturnBooResponseDto() throws Exception {
        final BookRequestDto requestDto = BookRequestDto.builder()
                .title(UPDATE_BOOK_TITLE_TEST_TITLE)
                .author(UPDATE_BOOK_AUTHOR_TEST_AUTHOR)
                .categoryIds(CREATE_BOOK_CATEGORIES_LORD_OF_THE_RINGS)
                .isbn(CREATE_BOOK_ISBN_LORD_OF_THE_RINGS)
                .price(CREATE_BOOK_PRICE_LORD_OF_THE_RINGS)
                .build();
        final BookResponseDto expected = BookResponseDto.builder()
                .title(requestDto.title())
                .author(requestDto.author())
                .categoryIds(requestDto.categoryIds())
                .isbn(requestDto.isbn())
                .price(requestDto.price())
                .build();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String url = URI + "/" + VALID_BOOK_ID_HOBBIT;

        MvcResult result = mockMvc.perform(put(url)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        BookResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto.class);
        assertNotNull(actual);
        assertNotNull(actual.id());
        assertEquals(VALID_BOOK_ID_HOBBIT, actual.id());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @DisplayName("""
            Get existing book by ID,
            expected status - 200, expected response - BookResponseDto
            """)
    @WithMockUser
    void getBookById_ExistingBookId_ReturnBookResponseDto() throws Exception {
        BookResponseDto expected = getHobbitBookResponseDto();
        String url = URI + "/" + VALID_BOOK_ID_HOBBIT;

        MvcResult result = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto.class);
        assertNotNull(actual);
        assertNotNull(actual.id());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("""
            Get three existing books,
            expected status - 200, expected response - BookResponseDto[]
            """)
    @WithMockUser
    void getAll_ReturnTwoBookResponseDtoAtFirstPageSizeTwoSortByIdAsc() throws Exception {
        BookResponseDto responseDtoHarryPotter = getHarryPotterBookResponseDto();
        BookResponseDto responseDtoHobbit = getHobbitBookResponseDto();
        List<BookResponseDto> expected = List.of(responseDtoHarryPotter, responseDtoHobbit);

        MvcResult result = mockMvc.perform(get(URI)
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto[].class);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @DisplayName("""
            Delete existing book by ID, expected status - 200
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void deleteBook_ExistBookId_ResponseNoContent() throws Exception {
        String url = URI + "/" + VALID_BOOK_ID_HARRY_POTTER_CHAMBER_OF_SECRETS;

        mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("""
            "Search books by params,
            expected status - 200, expected response - BookResponseDto[]
            """)
    @WithMockUser
    void searchBooks_ValidSearchParams_ReturnExpectedBookResponseDto() throws Exception {
        BookResponseDto responseDtoHobbit = getHobbitBookResponseDto();
        List<BookResponseDto> expected = List.of(responseDtoHobbit);

        MvcResult result = mockMvc.perform(get(URI + "/search")
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "id")
                        .param("titles","The Hobbit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto[].class);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.length);
    }

    private static BookResponseDto getHobbitBookResponseDto() {
        return BookResponseDto.builder()
                .id(VALID_BOOK_ID_HOBBIT)
                .title(VALID_BOOK_TITLE_HOBBIT)
                .author(VALID_BOOK_AUTHOR_TOLKIEN)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HOBBIT)
                .isbn(VALID_BOOK_ISBN_HOBBIT)
                .price(VALID_BOOK_PRICE_HOBBIT)
                .build();
    }

    private static BookResponseDto getHarryPotterBookResponseDto() {
        return BookResponseDto.builder()
                .id(VALID_BOOK_ID_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .title(VALID_BOOK_TITLE_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .author(VALID_BOOK_AUTHOR_ROWLING)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .isbn(VALID_BOOK_ISBN_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .price(VALID_BOOK_PRICE_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .build();
    }
}
