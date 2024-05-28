package com.gmail.woosay333.onlinebookstore.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequestDto(
        @NotBlank(message = "Value of the Shipping Address field can't be null or empty")
        @Size(max = 255, message = "The length of the Shipping Address field "
                + "cannot be longer than 255 characters")
        @Schema(example = "37173 Ritchie Parks, Goodwinville, AK 94927, USA")
        String shippingAddress
) {
}
