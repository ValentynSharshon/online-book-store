package com.gmail.woosay333.onlinebookstore.mapper;

import com.gmail.woosay333.onlinebookstore.config.MapperConfig;
import com.gmail.woosay333.onlinebookstore.dto.book.BookResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.book.BookRequestDto;
import com.gmail.woosay333.onlinebookstore.entity.Book;
import com.gmail.woosay333.onlinebookstore.entity.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    @Mapping(target = "categoryIds", source = "categories", qualifiedByName = "setCategoryIds")
    BookResponseDto toDto(Book book);

    @Named("setCategoryIds")
    default Set<Long> setCategoryIds(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoryById")
    Book toModel(BookRequestDto requestDto);

    @Named("categoryById")
    default Set<Category> categoryById(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
