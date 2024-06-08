package com.gmail.woosay333.onlinebookstore.service.category;

import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories(Pageable pageable);

    CategoryResponseDto getCategoryById(Long id);

    CategoryResponseDto createNewCategory(CategoryRequestDto categoryDto);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDto);

    void deleteCategory(Long id);

    Set<Long> getAllExistedCategoryIdsFromIds(Set<Long> ids);
}
