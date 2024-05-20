package com.gmail.woosay333.onlinebookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "The value of the Name field "
            + "cannot be null or empty")
    @Size(max = 255,
            message = "The length of the Name field "
                    + "cannot be longer than 255 characters")
    @Schema(example = "Name of the Category")
    private String name;

    @Size(max = 512,
            message = "The length of the Description field "
                    + "cannot be longer than 512 characters")
    @Schema(example = "Description of the Category")
    private String description;
}
