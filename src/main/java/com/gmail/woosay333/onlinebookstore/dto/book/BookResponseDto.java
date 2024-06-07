package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;

@Builder
public record BookResponseDto(
        @Schema(example = "5")
        Long id,
        @Schema(example = "[1, 3, 5]")
        Set<Long> categoryIds,
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
