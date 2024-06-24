package com.gmail.woosay333.onlinebookstore.service.category.impl;

import static com.gmail.woosay333.onlinebookstore.util.TestData.INVALID_CATEGORY_ID_5L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_DESCRIPTION_FANTASY;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_DESCRIPTION_NOVEL;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_ID_1L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_ID_2L;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_NAME_FANTASY;
import static com.gmail.woosay333.onlinebookstore.util.TestData.VALID_CATEGORY_NAME_NOVEL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Category;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.CategoryMapper;
import com.gmail.woosay333.onlinebookstore.repository.category.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    @DisplayName("""
            Get all categories, return list of CategoryResponseDto
            """)
    void getAllCategories_Pageable_ReturnListOfCategoryResponseDto() {
        Pageable pageable = Pageable.unpaged();
        List<Category> categoriesFromDb = List.of(
                createCategory(VALID_CATEGORY_NAME_FANTASY, VALID_CATEGORY_DESCRIPTION_FANTASY),
                createCategory(VALID_CATEGORY_NAME_NOVEL, VALID_CATEGORY_DESCRIPTION_NOVEL)
        );
        PageImpl<Category> categories = new PageImpl<>(categoriesFromDb);

        when(categoryRepository.findAll(any(Pageable.class)))
                .thenReturn(categories);
        when(categoryMapper.toResponseDto(any(Category.class)))
                .thenReturn(getValidCategoryResponseDto());

        List<CategoryResponseDto> actual = categoryService.getAllCategories(pageable);
        assertEquals(categoriesFromDb.size(), actual.size());
    }

    @Test
    @DisplayName("""
            Get existing category by id
            """)
    void getCategoryById_ExistingCategoryId_ReturnCategoryResponseDto() {
        Category existingCategory = createCategory(
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );
        existingCategory.setId(VALID_CATEGORY_ID_1L);
        CategoryResponseDto responseDto = getValidCategoryResponseDto();

        when(categoryRepository.findById(VALID_CATEGORY_ID_1L))
                .thenReturn(Optional.of(existingCategory));
        when(categoryMapper.toResponseDto(existingCategory))
                .thenReturn(responseDto);

        CategoryResponseDto actual = categoryService.getCategoryById(VALID_CATEGORY_ID_1L);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(responseDto, actual);
    }

    @Test
    @DisplayName("""
            Get non-existing category by id, throws exception
            """)
    void getCategoryById_NonExistingCategoryId_ThrowException() {
        when(categoryRepository.findById(INVALID_CATEGORY_ID_5L))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getCategoryById(INVALID_CATEGORY_ID_5L));
        assertEquals(String.format("Can't find category by id:%d", INVALID_CATEGORY_ID_5L),
                exception.getMessage());
    }

    @Test
    @DisplayName("""
            Create new category successfully
            """)
    void createNewCategory_ValidCategoryRequestDto_ReturnCategoryResponseDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );
        Category categoryToSave = createCategory(
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );
        Category savedCategory = createCategory(
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );
        savedCategory.setId(VALID_CATEGORY_ID_1L);

        when(categoryMapper.toModel(requestDto)).thenReturn(categoryToSave);
        when(categoryRepository.save(categoryToSave)).thenReturn(savedCategory);
        when(categoryMapper.toResponseDto(savedCategory)).thenReturn(getValidCategoryResponseDto());

        CategoryResponseDto actual = categoryService.createNewCategory(requestDto);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(requestDto, actual);
    }

    @Test
    @DisplayName("""
            Update existing category, return CategoryResponseDto
            """)
    void updateCategory_ExistingCategoryIdAndValidCategoryRequestDto_ReturnsCategoryResponseDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );
        Category updatedCategory = createCategory(
                VALID_CATEGORY_NAME_FANTASY,
                VALID_CATEGORY_DESCRIPTION_FANTASY
        );
        updatedCategory.setId(VALID_CATEGORY_ID_1L);
        CategoryResponseDto responseDto = getValidCategoryResponseDto();

        when(categoryRepository.existsById(VALID_CATEGORY_ID_1L)).thenReturn(true);
        when(categoryMapper.toModel(requestDto)).thenReturn(updatedCategory);
        when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        when(categoryMapper.toResponseDto(updatedCategory)).thenReturn(responseDto);

        CategoryResponseDto actual = categoryService.updateCategory(
                VALID_CATEGORY_ID_1L,
                requestDto
        );

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(responseDto, actual);
    }

    @Test
    @DisplayName("""
            Update non-existing category, throws exception
            """)
    void updateCategory_NonExistingCategoryId_ThrowsException() {
        when(categoryRepository.existsById(VALID_CATEGORY_ID_1L))
                .thenReturn(false);

        Exception actual = assertThrows(EntityNotFoundException.class,
                () -> categoryService.updateCategory(VALID_CATEGORY_ID_1L,
                        new CategoryRequestDto(
                                VALID_CATEGORY_NAME_FANTASY,
                                VALID_CATEGORY_DESCRIPTION_FANTASY
                        )
                )
        );

        assertEquals(String.format("Can't find category by id %d", VALID_CATEGORY_ID_1L),
                actual.getMessage());
    }

    @Test
    @DisplayName("""
            Delete existing category by ID, returns void
            """)
    void deleteById_ExistingCategoryId_SuccessfullyDeleted() {
        when(categoryRepository.findById(VALID_CATEGORY_ID_1L))
                .thenReturn(Optional.of(createCategory(
                        VALID_CATEGORY_NAME_FANTASY,
                        VALID_CATEGORY_DESCRIPTION_FANTASY)
                ));
        doNothing().when(categoryRepository).deleteById(VALID_CATEGORY_ID_1L);

        assertDoesNotThrow(() -> categoryService.deleteCategory(VALID_CATEGORY_ID_1L));
    }

    @Test
    @DisplayName("""
            Delete non-existing category by ID, throws exception
            """)
    void deleteCategory_NonExistingCategoryId_ThrowsException() {
        when(categoryRepository.findById(VALID_CATEGORY_ID_1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.deleteCategory(VALID_CATEGORY_ID_1L));

        assertEquals(String.format("Can't find category to delete by id %d",
                VALID_CATEGORY_ID_1L), exception.getMessage());
    }

    @Test
    @DisplayName("""
            Get all existing category IDs from provided IDs,
            return set of existing IDs
            """)
    void getAllExistedCategoryIdsFromIds_ValidIds_ReturnExistingCategoryIds() {
        Set<Long> categoryIds = Set.of(
                VALID_CATEGORY_ID_1L,
                VALID_CATEGORY_ID_2L,
                INVALID_CATEGORY_ID_5L
        );
        Set<Category> existingCategories = Set.of(
                new Category(VALID_CATEGORY_ID_1L),
                new Category(VALID_CATEGORY_ID_2L)
        );

        when(categoryRepository.findAllByIdIn(categoryIds)).thenReturn(existingCategories);

        Set<Long> actual = categoryService.getAllExistedCategoryIdsFromIds(categoryIds);
        Set<Long> expected = existingCategories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all existing category IDs from empty list of IDs, returns empty collection")
    void getAllExistedCategoryIdsFromIds_EmptyIds_ReturnEmptySet() {
        Set<Long> emptyCategoryIds = Collections.emptySet();

        when(categoryRepository.findAllByIdIn(emptyCategoryIds))
                .thenReturn(Collections.emptySet());

        Set<Long> actual = categoryService.getAllExistedCategoryIdsFromIds(emptyCategoryIds);

        assertTrue(actual.isEmpty());
    }

    private static Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    private static CategoryResponseDto getValidCategoryResponseDto() {
        return CategoryResponseDto.builder()
                .id(VALID_CATEGORY_ID_1L)
                .name(VALID_CATEGORY_NAME_FANTASY)
                .description(VALID_CATEGORY_DESCRIPTION_FANTASY)
                .build();
    }
}
