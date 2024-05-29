package com.gmail.woosay333.onlinebookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryResponseDto(
        @Schema(example = "12")
        Long id,
        @Schema(example = "Fiction")
        String name,
        @Schema(example = "Here should be some description of the category",
                nullable = true)
        String description
) {
}
