package com.gmail.woosay333.onlinebookstore.controller;

import com.gmail.woosay333.onlinebookstore.dto.user.UserLoginRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserLoginResponseDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserResponseDto;
import com.gmail.woosay333.onlinebookstore.exception.RegistrationException;
import com.gmail.woosay333.onlinebookstore.security.AuthenticationService;
import com.gmail.woosay333.onlinebookstore.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication management.",
        description = "Endpoints for registration and login operations.")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user.",
            description = "Register a new user with unique email.")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto userRegistrationRequestDto
    ) throws RegistrationException {
        return userService.register(userRegistrationRequestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Login user.",
            description = "Login user by email and password. Returned JWT token in response.")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }
}
