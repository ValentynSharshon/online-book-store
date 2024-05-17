package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookSearchParameters(
        @Schema(description = "Filtering the result list by book title",
                example = "The Great Gatsby")
        String[] titles,
        @Schema(description = "Filtering the result list by book category ID",
                example = "[1, 5, 8]")
        String[] categoryIds,
        @Schema(description = "Filtering the result list by book author",
                example = "J.R.R. Tolkien")
        String[] authors,
        @Schema(description = "Filtering the result list by book ISBN",
                example = "9780143034902")
        String[] isbns,
        @Schema(description = "Minimal price for filtering",
                example = "10.99")
        Long minPrice,
        @Schema(description = "Maximal price for filtering",
                example = "20.99")
        Long maxPrice
) {
}
