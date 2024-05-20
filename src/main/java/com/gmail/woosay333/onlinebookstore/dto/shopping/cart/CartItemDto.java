package com.gmail.woosay333.onlinebookstore.dto.shopping.cart;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private String bookTitle;
    private Long bookId;
    private int quantity;
}
