package com.gmail.woosay333.onlinebookstore.service.user;

import com.gmail.woosay333.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserResponseDto;
import com.gmail.woosay333.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRequestDto) throws
            RegistrationException;
}
