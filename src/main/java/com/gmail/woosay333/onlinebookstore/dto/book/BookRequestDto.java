package com.gmail.woosay333.onlinebookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
@Builder
public class BookRequestDto {
    private static final int SIZE_MAX_VALUE_255 = 255;
    private static final int SIZE_MAX_VALUE_512 = 512;

    @NotBlank(message = "The value of the Title field "
            + "cannot be null or empty")
    @Size(max = SIZE_MAX_VALUE_255, message = "The length of the Title field "
            + "cannot be longer than 255 characters")
    @Schema(example = "Title of the book")
    private String title;

    @NotBlank(message = "The value of the Author field "
            + "cannot be null or empty")
    @Size(max = SIZE_MAX_VALUE_255, message = "The length of the Author field "
            + "cannot be longer than 255 characters")
    @Schema(example = "Author of the book")
    private String author;

    @NotNull(message = "The value of the ISBN field "
            + "cannot be null")
    @ISBN(type = ISBN.Type.ISBN_13, message = "The value of the ISBN field "
            + "must match the pattern")
    @Schema(example = "9780143034902")
    private String isbn;

    @NotNull(message = "The value of the Price field "
            + "cannot be null")
    @Positive(message = "The value of the Price field "
            + "must be greater than 0")
    @Schema(example = "12.99")
    private BigDecimal price;

    @Size(max = SIZE_MAX_VALUE_512, message = "The length of the Description field "
            + "cannot be longer than 512 characters")
    @Schema(example = "Description of the book", nullable = true)
    private String description;

    @Size(max = SIZE_MAX_VALUE_255, message = "The length of the Cover Image field "
            + "cannot be longer than 255 characters")
    @Schema(example = "https://images.example.com/shadow_of_the_wind.jpg", nullable = true)
    private String coverImage;
}
