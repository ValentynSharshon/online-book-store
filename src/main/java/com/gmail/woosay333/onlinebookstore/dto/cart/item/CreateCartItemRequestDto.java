package com.gmail.woosay333.onlinebookstore.dto.cart.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotNull
    private Long bookId;
    @Positive
    private int quantity;
}
