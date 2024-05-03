package com.gmail.woosay333.onlinebookstore.dto;

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
    private String title;

    @NotBlank(message = "The value of the Author field "
            + "cannot be null or empty")
    @Size(max = SIZE_MAX_VALUE_255, message = "The length of the Author field "
            + "cannot be longer than 255 characters")
    private String author;

    @NotNull(message = "The value of the ISBN field "
            + "cannot be null")
    @ISBN(type = ISBN.Type.ISBN_13, message = "The value of the ISBN field "
            + "must match the pattern")
    private String isbn;

    @NotNull(message = "The value of the Price field "
            + "cannot be null")
    @Positive(message = "The value of the Price field "
            + "must be greater than 0")
    private BigDecimal price;

    @Size(max = SIZE_MAX_VALUE_512, message = "The length of the Description field "
            + "cannot be longer than 512 characters")
    private String description;

    @Size(max = SIZE_MAX_VALUE_255, message = "The length of the Cover Image field "
            + "cannot be longer than 255 characters")
    private String coverImage;
}
