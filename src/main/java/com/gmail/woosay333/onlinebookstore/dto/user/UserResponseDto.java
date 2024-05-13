package com.gmail.woosay333.onlinebookstore.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponseDto {
    @Schema(example = "56")
    private Long id;

    @Schema(example = "example@email.com")
    private String email;

    @Schema(example = "Bob")
    private String firstName;

    @Schema(example = "Bobson")
    private String lastName;

    @Schema(example = "37173 Ritchie Parks, Goodwinville, AK 94927, USA")
    private String shippingAddress;
}
