package com.gmail.woosay333.onlinebookstore.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotBlank
        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
        @Schema(example = "example@example.com")
        String email,
        @NotBlank
        @Length(min = 5, max = 24)
        @Schema(example = "passwordExample")
        String password
) {
}
