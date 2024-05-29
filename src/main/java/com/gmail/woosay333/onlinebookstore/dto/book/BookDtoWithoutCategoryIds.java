package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record BookDtoWithoutCategoryIds(
        @Schema(example = "10")
        Long id,
        @Schema(example = "Title of the book")
        String title,
        @Schema(example = "Book Author")
        String author,
        @Schema(example = "0061964360159")
        String isbn,
        @Schema(example = "10.99")
        BigDecimal price,
        @Schema(example = "About book",
                nullable = true)
        String description,
        @Schema(description = "Link on book cover",
                example = "https://example.com/cover-image.jpg",
                nullable = true)
        String coverImage
) {
}
