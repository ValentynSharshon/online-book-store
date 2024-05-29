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
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryMapper.toResponseDto(
                categoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Can't find category by id:" + id)));
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryDto) {
        Category category = categoryMapper.toModel(categoryDto);
        return categoryMapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find category by id " + id);
        }
        Category category = categoryMapper.toModel(categoryDto);
        category.setId(id);
        return categoryMapper.toResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Can't find category to delete by id " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Set<Long> getAllExistedCategoryIdsFromIds(Set<Long> ids) {
        return categoryRepository.findAllByIdIn(ids).stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }
}
