package com.gmail.woosay333.onlinebookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryResponseDto {
    @Schema(example = "56")
    private Long id;

    @Schema(example = "Fantasy")
    private String name;

    @Schema(example = "This books genre is characterized by elements of magic "
            + "or the supernatural and is often inspired by mythology or folklore")
    private String description;
}
