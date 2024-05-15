package com.gmail.woosay333.onlinebookstore.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank(message = "The value of the Email field cannot be null or empty")
        @Email(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "The value of the Email field does not match the pattern")
        @Schema(example = "example@email.com")
        String email,
        @NotBlank(message = "The value of the Password field cannot be null or empty")
        @Size(min = 5, max = 20,
                message = "The Password field should be between 5 and 20 characters long")
        @Schema(example = "examplePassword")
        String password
) {
}
