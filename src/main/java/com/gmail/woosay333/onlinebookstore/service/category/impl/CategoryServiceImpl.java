package com.gmail.woosay333.onlinebookstore.service.category.impl;

import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Category;
import com.gmail.woosay333.onlinebookstore.exception.EntityNotFoundException;
import com.gmail.woosay333.onlinebookstore.mapper.CategoryMapper;
import com.gmail.woosay333.onlinebookstore.repository.category.CategoryRepository;
import com.gmail.woosay333.onlinebookstore.service.category.CategoryService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.save(categoryMapper.toModel(categoryRequestDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can`t find a category with id: %d", id)
                )));
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        isCategoryExist(id);
        Category category = categoryMapper.toModel(categoryRequestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        isCategoryExist(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public Set<Long> getAllExistedCategoryIdsFromIds(Set<Long> categoryIds) {
        return categoryRepository.findAllByIdIn(categoryIds)
                .stream().map(Category::getId)
                .collect(Collectors.toSet());
    }

    private void isCategoryExist(Long id) {
        if (!categoryRepository.existsCategoryById(id)) {
            throw new EntityNotFoundException(
                    String.format("Can`t find a category with id: %d", id)
            );
        }
    }
}
