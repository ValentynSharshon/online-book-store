package com.gmail.woosay333.onlinebookstore.dto.cart.item;

import lombok.Data;

@Data
public class CartItemResponseDto {
    private Long id;
    private String bookTitle;
    private Long bookId;
    private int quantity;
}
