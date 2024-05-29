package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;

@Builder
public record BookDto(
        @Schema(example = "15")
        Long id,
        @Schema(example = "[1, 2, 5]")
        Set<Long> categoryIds,
        @Schema(example = "Title of the book")
        String title,
        @Schema(example = "Book Author")
        String author,
        @Schema(example = "0-061-96436-0")
        String isbn,
        @Schema(example = "25.99")
        BigDecimal price,
        @Schema(example = "About book", nullable = true)
        String description,
        @Schema(description = "Link on book cover",
                example = "https://example.com/cover-image.jpg",
                nullable = true)
        String coverImage
) {
}
