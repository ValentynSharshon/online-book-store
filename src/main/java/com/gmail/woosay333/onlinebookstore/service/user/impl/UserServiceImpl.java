package com.gmail.woosay333.onlinebookstore.service.user.impl;

import com.gmail.woosay333.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserResponseDto;
import com.gmail.woosay333.onlinebookstore.exception.RegistrationException;
import com.gmail.woosay333.onlinebookstore.mapper.UserMapper;
import com.gmail.woosay333.onlinebookstore.repository.user.UserRepository;
import com.gmail.woosay333.onlinebookstore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findUserByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException(String.format("User with email %s already exists",
                            userRegistrationRequestDto.getEmail()));
        }
        return mapper.toDto(userRepository.save(mapper.toModel(userRegistrationRequestDto)));
    }
}
