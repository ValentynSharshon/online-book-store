package com.gmail.woosay333.onlinebookstore.dto.quantity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record QuantityDto(
        @NotEmpty
        @Min(value = 0)
        @Schema(example = "10", nullable = true)
        int quantity
) {
}
