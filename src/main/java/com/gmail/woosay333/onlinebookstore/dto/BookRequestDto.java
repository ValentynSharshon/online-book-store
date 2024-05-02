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

    @NotBlank(message = "{validation.book.title.not.blank}")
    @Size(max = SIZE_MAX_VALUE_255, message = "{validation.book.title.size}")
    private String title;

    @NotBlank(message = "{validation.book.author.not.blank}")
    @Size(max = SIZE_MAX_VALUE_255, message = "{validation.book.author.size}")
    private String author;

    @NotNull(message = "{validation.book.isbn.not.null}")
    @ISBN(type = ISBN.Type.ISBN_13, message = "{validation.book.isbn.pattern}")
    private String isbn;

    @NotNull(message = "{validation.book.price.not.null}")
    @Positive(message = "{validation.book.price.positive}")
    private BigDecimal price;

    @Size(max = SIZE_MAX_VALUE_512, message = "{validation.book.description.size}")
    private String description;

    @Size(max = SIZE_MAX_VALUE_255, message = "{validation.book.cover.image.size}")
    private String coverImage;
}
