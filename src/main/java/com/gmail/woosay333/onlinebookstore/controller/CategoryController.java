package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.category.CategoryResponseDto;
import com.gmail.woosay333.onlinebookstore.service.book.BookService;
import com.gmail.woosay333.onlinebookstore.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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

@Tag(name = "Book management",
        description = "Endpoints for managing categories")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category",
            description = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public CategoryResponseDto createNewCategory(
            @RequestBody @Valid CategoryRequestDto categoryDto
    ) {
        return categoryService.createNewCategory(categoryDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all of categories",
            description = "Return all of categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public List<CategoryResponseDto> getAllCategories(
            @ParameterObject @PageableDefault(sort = "name", value = 5) Pageable pageable
    ) {
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return category",
            description = "Return single category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update a category",
            description = "Update a category if exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public CategoryResponseDto updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDto categoryDto
    ) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category by id",
            description = "Delete a category by id if exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content - successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not found - wrong id",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}/books")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return page of books by category",
            description = "Return page of books with pagination and sorting by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable Long id,
            @ParameterObject @PageableDefault(sort = "title", value = 5) Pageable pageable
    ) {
        return bookService.getBooksByCategoryId(id, pageable);
    }
}
