package com.gmail.woosay333.onlinebookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank
        @Size(max = 255)
        @Schema(example = "Fiction")
        String name,
        @NotBlank
        @Size(max = 512)
        @Schema(example = "Here should be some description of the category",
                nullable = true)
        String description
) {
}
