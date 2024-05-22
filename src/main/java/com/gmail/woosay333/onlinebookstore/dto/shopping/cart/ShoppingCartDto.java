package com.gmail.woosay333.onlinebookstore.dto.shopping.cart;

import com.gmail.woosay333.onlinebookstore.dto.cart.item.CartItemResponseDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
