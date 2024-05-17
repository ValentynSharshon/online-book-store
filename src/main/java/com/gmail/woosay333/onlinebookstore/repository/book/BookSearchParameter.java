package com.gmail.woosay333.onlinebookstore.repository.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSearchParameter {
    TITLE("title"),
    CATEGORY("category"),
    AUTHOR("author"),
    ISBN("isbn"),
    PRICE("price");

    private final String name;
}
