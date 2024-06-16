package com.gmail.woosay333.onlinebookstore.controller;

import static com.gmail.woosay333.onlinebookstore.util.TestData.DELETE_VALUES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_BOOKS_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.INSERT_CATEGORIES_SQL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.UPDATE_CATEGORY_DESCRIPTION;
import static com.gmail.woosay333.onlinebookstore.util.TestData.UPDATE_CATEGORY_TITLE;
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
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_DESCRIPTION_FANTASY;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_DESCRIPTION_NOVEL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_ID_1L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_ID_2L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_NAME_FANTASY;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_NAME_NOVEL;
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
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
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
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    private static final String URI = "/categories";
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
            Create new category,
            expected status - 201, expected response - CategoryResponseDto
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void createNewCategory_ValidCategoryRequestDto_ReturnExpectedCategoryResponseDto()
            throws Exception {
        CategoryRequestDto requestDto = getCategoryRequestDto(
                VALID_CATEGORY_NAME_NOVEL,
                VALID_CATEGORY_DESCRIPTION_NOVEL
        );
        CategoryResponseDto expected = getCategoryResponseDto(
                VALID_CATEGORY_ID_1L,
                VALID_CATEGORY_NAME_NOVEL,
                VALID_CATEGORY_DESCRIPTION_NOVEL
        );
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post(URI)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryResponseDto.class);
        assertNotNull(actual);
        assertNotNull(actual.id());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @DisplayName("""
            Update existing category,
            expected status - 202, expected response - CategoryResponseDto
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void updateCategory_UpdateExistingCategory_ReturnsExpectedCategoryDto() throws Exception {
        CategoryRequestDto requestDto = getCategoryRequestDto(
                UPDATE_CATEGORY_TITLE,
                UPDATE_CATEGORY_DESCRIPTION
        );
        CategoryResponseDto expected = getCategoryResponseDto(
                VALID_CATEGORY_ID_1L,
                UPDATE_CATEGORY_TITLE,
                UPDATE_CATEGORY_DESCRIPTION
        );
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String url = URI + "/" + VALID_CATEGORY_ID_1L;

        MvcResult result = mockMvc.perform(put(url)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        CategoryResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryResponseDto.class);
        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("""
            Get two existing categories,
            expected status - 200, expected response - CategoryResponseDto[]
            """)
    @WithMockUser
    void getAllCategories_ReturnExpectedCategoryDto() throws Exception {
        List<CategoryResponseDto> expected = List.of(
                getCategoryResponseDto(
                        VALID_CATEGORY_ID_1L,
                        VALID_CATEGORY_NAME_FANTASY,
                        VALID_CATEGORY_DESCRIPTION_FANTASY
                ),
                getCategoryResponseDto(
                        VALID_CATEGORY_ID_2L,
                        VALID_CATEGORY_NAME_NOVEL,
                        VALID_CATEGORY_DESCRIPTION_NOVEL
                )
        );

        MvcResult result = mockMvc.perform(get(URI)
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryResponseDto[].class);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @DisplayName("""
            Get category by existing ID,
            expected status - 200, expected response - CategoryResponseDto
            """)
    @WithMockUser
    void getCategoryById_ExistCategoryId_ReturnExpectedCategoryResponseDto() throws Exception {
        String url = URI + "/" + VALID_CATEGORY_ID_1L;
        CategoryResponseDto expected = getCategoryResponseDto(
                VALID_CATEGORY_ID_1L,
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );

        MvcResult result = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), CategoryResponseDto.class);
        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    @DisplayName("""
            Delete existing category, expected status - 204
            """)
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void deleteCategory_ExistingCategoryId_ResponseNoContent() throws Exception {
        String url = URI + "/" + VALID_CATEGORY_ID_1L;

        mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("""
            Get all books by existing category by ID,
            expected status - 200, expected response - BookResponseDto[]
            """)
    @WithMockUser
    void getBooksByCategoryId_ValidCategoryId_ReturnExpectedBookResponseDto(
            @Autowired DataSource dataSource) throws Exception {
        insertData(dataSource);
        BookResponseDto bookHarryPotter = BookResponseDto.builder()
                .id(VALID_BOOK_ID_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .title(VALID_BOOK_TITLE_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .author(VALID_BOOK_AUTHOR_ROWLING)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .isbn(VALID_BOOK_ISBN_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .price(VALID_BOOK_PRICE_HARRY_POTTER_CHAMBER_OF_SECRETS)
                .build();
        BookResponseDto bookHobbit = BookResponseDto.builder()
                .id(VALID_BOOK_ID_HOBBIT)
                .title(VALID_BOOK_TITLE_HOBBIT)
                .author(VALID_BOOK_AUTHOR_TOLKIEN)
                .categoryIds(VALID_BOOK_CATEGORY_IDS_HOBBIT)
                .isbn(VALID_BOOK_ISBN_HOBBIT)
                .price(VALID_BOOK_PRICE_HOBBIT)
                .build();
        List<BookResponseDto> expected = List.of(bookHarryPotter, bookHobbit);
        String url = URI + "/" + VALID_CATEGORY_ID_1L + "/books";

        MvcResult result = mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        BookResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookResponseDto[].class);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.length);
        assertEquals(bookHarryPotter.title(), actual[0].title());
        assertEquals(bookHobbit.title(), actual[1].title());
    }

    private static CategoryRequestDto getCategoryRequestDto(String name, String description) {
        return new CategoryRequestDto(name, description);
    }

    private static CategoryResponseDto getCategoryResponseDto(Long id,
                                                              String name,
                                                              String description) {
        return CategoryResponseDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    @SneakyThrows
    private void insertData(@Autowired DataSource dataSource) {
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
}
