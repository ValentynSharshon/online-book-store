package com.gmail.woosay333.onlinebookstore.dto.order;

import com.gmail.woosay333.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        @Schema(example = "25")
        Long id,
        @Schema(example = "15")
        Long userId,
        List<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        @Schema(example = "19.99")
        BigDecimal total,
        @Schema(example = "DELIVERED")
        String status
) {
}
