package com.gmail.woosay333.onlinebookstore.dto.user;

import com.gmail.woosay333.onlinebookstore.validation.FieldMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(first = "password",
        second = "repeatPassword",
        message = "The values of the Password and Repeated Password fields do not match")
public class UserRegistrationRequestDto {
    @NotBlank(message = "The value of the Email field cannot be null or empty")
    @Email(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "The value of the Email field does not match the pattern")
    @Schema(example = "User registration email")
    private String email;

    @NotBlank(message = "The value of the Password field cannot be null or empty")
    @Size(min = 5, max = 20,
            message = "The Password field should be between 5 and 20 characters long")
    @Schema(example = "User registration password")
    private String password;

    @Schema(example = "User registration repeated password")
    private String repeatPassword;

    @NotBlank(message = "The value of the First Name field cannot be null or empty")
    @Schema(example = "User registration first name")
    private String firstName;

    @NotBlank(message = "The value of the Last Name field cannot be null or empty")
    @Schema(example = "User registration last name")
    private String lastName;

    @Schema(example = "User registration shipping address")
    private String shippingAddress;
}
