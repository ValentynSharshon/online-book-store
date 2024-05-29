package com.gmail.woosay333.onlinebookstore.dto.cart.item;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartItemResponseDto(
        @Schema(example = "25", nullable = true)
        Long id,
        @Schema(example = "15", nullable = true)
        Long bookId,
        @Schema(example = "Title example", nullable = true)
        String bookTitle,
        @Schema(example = "10", nullable = true)
        int quantity
) {
}
