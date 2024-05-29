package com.gmail.woosay333.onlinebookstore.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDto(
        @Schema(example = "25")
        Long id,
        @Schema(example = "example@example.com")
        String email,
        @Schema(example = "Bob")
        String firstName,
        @Schema(example = "Bobson")
        String lastName,
        @Schema(example = "123 Main Street, Anytown, USA 12345")
        String shippingAddress
) {
}
