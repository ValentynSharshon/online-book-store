package com.gmail.woosay333.onlinebookstore.security;

import com.gmail.woosay333.onlinebookstore.dto.user.UserLoginRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(), requestDto.password()));

        String generatedToken = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(generatedToken);
    }
}
