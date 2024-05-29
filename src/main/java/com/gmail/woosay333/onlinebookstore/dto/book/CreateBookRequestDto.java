package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateBookRequestDto(
        @Schema(example = "Book title")
        @NotBlank
        String title,
        @Schema(example = "[1, 5 ,3, 8]")
        @NotEmpty
        Set<Long> categoryIds,
        @Schema(example = "Book Author")
        @NotBlank
        String author,
        @Schema(example = "0061964360895")
        @NotBlank
        String isbn,
        @NotNull
        @Min(0)
        @Schema(example = "10.99")
        BigDecimal price,
        @Schema(example = "About book", nullable = true)
        String description,
        @Schema(example = "https://example.com/cover-image.jpg", nullable = true)
        String coverImage
) {
}
