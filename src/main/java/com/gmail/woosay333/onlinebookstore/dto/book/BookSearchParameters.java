package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookSearchParameters(
        @Schema(description = "Filtering the result list by book title",
                example = "The Great Gatsby")
        String[] name,
        @Schema(description = "Filtering the result list by book author",
                example = "F. Scott Fitzgerald")
        String[] provider) {
}
