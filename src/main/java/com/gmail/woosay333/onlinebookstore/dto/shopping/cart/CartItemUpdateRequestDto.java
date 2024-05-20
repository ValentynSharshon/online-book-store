package com.gmail.woosay333.onlinebookstore.dto.shopping.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemUpdateRequestDto {
    @Positive
    private int quantity;
}
