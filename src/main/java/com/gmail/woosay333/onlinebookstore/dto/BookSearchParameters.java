package com.gmail.woosay333.onlinebookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookSearchParameters(
        @Schema(description = "List of titles search params",
                example = "book")
        String[] titles,
        @Schema(description = "List of author search params",
                example = "author")
        String[] authors) {
}
