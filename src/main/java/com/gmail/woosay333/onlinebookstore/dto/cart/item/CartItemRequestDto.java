package com.gmail.woosay333.onlinebookstore.dto.cart.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record CartItemRequestDto(
        @NotEmpty
        @Schema(example = "25")
        Long bookId,
        @NotEmpty
        @Schema(example = "10")
        int quantity
) {
}
