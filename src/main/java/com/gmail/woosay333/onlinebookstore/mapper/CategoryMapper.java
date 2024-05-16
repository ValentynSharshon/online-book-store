package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.Category;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    List<CategoryResponseDto> toDtoList(List<Category> categoryList);

    Category toModel(CategoryRequestDto categoryDto);
}
