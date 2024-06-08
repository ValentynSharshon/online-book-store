package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record BookDtoWithoutCategoryIds(
        @Schema(example = "10")
        Long id,
        @Schema(example = "Book title")
        String title,
        @Schema(example = "Book Author")
        String author,
        @Schema(example = "9780142437247")
        String isbn,
        @Schema(example = "10.99")
        BigDecimal price,
        @Schema(example = "Short book description",
                nullable = true)
        String description,
        @Schema(description = "Book cover image link",
                example = "https://example.com/cover-image.jpg",
                nullable = true)
        String coverImage
) {
}
