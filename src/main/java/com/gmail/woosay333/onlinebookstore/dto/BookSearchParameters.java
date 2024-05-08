package com.gmail.woosay333.onlinebookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookSearchParameters(
        @Schema(description = "Filtering the result list by book title",
                example = "The Great Gatsby")
        String[] titles,
        @Schema(description = "Filtering the result list by book author",
                example = "F. Scott Fitzgerald")
        String[] authors) {
}
