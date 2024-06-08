package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;

@Builder
public record BookRequestDto(
        @Schema(example = "Book title")
        @NotBlank
        @Size(max = 255)
        String title,
        @Schema(example = "[1, 5 ,3]")
        @NotEmpty
        Set<Long> categoryIds,
        @Schema(example = "Book Author")
        @NotBlank
        @Size(max = 255)
        String author,
        @Schema(example = "9780142437247")
        @NotBlank
        String isbn,
        @NotNull
        @Min(0)
        @Schema(example = "10.99")
        BigDecimal price,
        @Schema(example = "Short book description",
                nullable = true)
        @Size(max = 512)
        String description,
        @Schema(description = "Book cover image link",
                example = "https://example.com/cover-image.jpg",
                nullable = true)
        @Size(max = 255)
        String coverImage
) {
}
