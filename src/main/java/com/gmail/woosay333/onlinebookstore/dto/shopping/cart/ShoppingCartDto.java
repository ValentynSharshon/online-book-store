package com.gmail.woosay333.onlinebookstore.dto.shopping.cart;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ShoppingCartDto(
        @Schema(example = "15", nullable = true)
        Long id,
        @Schema(example = "10", nullable = true)
        Long userId,
        @Schema(nullable = true)
        List<CartItemResponseDto> cartItems
) {
}
