package com.gmail.woosay333.onlinebookstore.dto.order;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        @Schema(example = "10",
                nullable = true)
        Long id,
        @Schema(example = "15",
                nullable = true)
        Long userId,
        @Schema(nullable = true)
        List<OrderItemResponseDto> orderItems,
        @Schema(nullable = true)
        LocalDateTime orderDate,
        @Schema(example = "155.28",
                nullable = true)
        BigDecimal total,
        @Schema(example = "DELIVERED",
                nullable = true)
        String status
) {
}
