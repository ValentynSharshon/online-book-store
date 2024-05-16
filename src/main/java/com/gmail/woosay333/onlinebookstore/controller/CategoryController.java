package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
import com.gmail.woosay333.onlinebookstore.service.book.BookService;
import com.gmail.woosay333.onlinebookstore.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Book management.", description = "Endpoints for managing categories.")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category.",
            description = "Create a new category and save to the database.")
    public CategoryResponseDto create(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a page of categories.",
            description = "Get a page of categories with pagination and sorting.")
    public List<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a single category by id.",
            description = "Get a single category by id.")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update a category by id.",
            description = "Update a category if exists by its id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDto categoryRequestDto
    ) {
        return categoryService.update(id, categoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category by id.",
            description = "Delete a category by id with soft delete.")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @GetMapping("/{id}/books")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Returns a single page of books.",
            description = "Return filtered page of books by category with pagination and sorting.")
    public List<BookDtoWithoutCategoryIds> getByCategoryId(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return bookService.getByCategoryId(id, pageable);
    }
}
