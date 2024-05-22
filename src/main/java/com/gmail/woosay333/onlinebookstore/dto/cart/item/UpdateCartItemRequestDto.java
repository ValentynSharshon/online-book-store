package com.gmail.woosay333.onlinebookstore.dto.cart.item;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @Positive
    private int quantity;
}
