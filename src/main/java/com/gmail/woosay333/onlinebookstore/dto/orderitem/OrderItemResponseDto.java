package com.gmail.woosay333.onlinebookstore.dto.orderitem;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderItemResponseDto(
        @Schema(example = "25", nullable = true)
        Long id,
        @Schema(example = "15", nullable = true)
        Long bookId,
        @Schema(example = "10", nullable = true)
        int quantity
) {
}
