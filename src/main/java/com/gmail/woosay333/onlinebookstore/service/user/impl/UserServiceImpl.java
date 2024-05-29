package com.gmail.woosay333.onlinebookstore.service.user.impl;

import com.gmail.woosay333.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.gmail.woosay333.onlinebookstore.dto.user.UserResponseDto;
import com.gmail.woosay333.onlinebookstore.entity.RoleName;
import com.gmail.woosay333.onlinebookstore.entity.User;
import com.gmail.woosay333.onlinebookstore.exception.RegistrationException;
import com.gmail.woosay333.onlinebookstore.mapper.UserMapper;
import com.gmail.woosay333.onlinebookstore.repository.role.RoleRepository;
import com.gmail.woosay333.onlinebookstore.repository.user.UserRepository;
import com.gmail.woosay333.onlinebookstore.service.user.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRequestDto) throws
            RegistrationException {

        if (userRepository.findByEmail(userRequestDto.email()).isPresent()) {
            throw new RegistrationException(
                    "User already exist with email " + userRequestDto.email());
        }

        User user = userMapper.toModel(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setRoles(roleRepository.getAllByNameIn(Set.of(RoleName.ROLE_USER)));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
