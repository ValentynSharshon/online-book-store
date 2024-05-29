package com.gmail.woosay333.onlinebookstore.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequestDto(
        @NotBlank
        @Size(min = 3, max = 255)
        @Schema(example = "37173 Ritchie Parks, Goodwinville, AK 94927, USA")
        String shippingAddress
) {
}
