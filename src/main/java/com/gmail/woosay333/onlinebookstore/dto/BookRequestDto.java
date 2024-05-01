package com.gmail.woosay333.onlinebookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestDto {
    @NotBlank(message = "The value of the Title field cannot be null or empty")
    @Size(max = 255, message = "The length of the Title field "
            + "cannot be longer than 255 characters")
    private String title;

    @NotBlank(message = "The value of the Author field cannot be null or empty")
    @Size(max = 255, message = "The length of the Author field "
            + "cannot be longer than 255 characters")
    private String author;

    @NotNull(message = "The value of the ISBN field cannot be null")
    @Pattern(regexp = "^\\d{13}$", message = "The value of the ISBN field "
            + "must exactly consist of 13 digits")
    private String isbn;

    @NotNull(message = "The value of the Price field cannot be null")
    @Positive(message = "The value of the Price field must be greater than 0")
    private BigDecimal price;

    @Size(max = 512, message = "The length of the Description field "
            + "cannot be longer than 512 characters")
    private String description;

    @Size(max = 255, message = "The length of the Cover Image field "
            + "cannot be longer than 255 characters")
    private String coverImage;
}
