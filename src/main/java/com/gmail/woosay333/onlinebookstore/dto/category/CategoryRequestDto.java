package com.gmail.woosay333.onlinebookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CategoryRequestDto(
        @NotBlank
        @Length(max = 50)
        @Schema(example = "Fiction")
        String name,
        @NotBlank
        @Length(max = 255)
        @Schema(example = "Here should be some description of the category",
                nullable = true)
        String description
) {
}
