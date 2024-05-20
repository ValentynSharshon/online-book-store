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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.existsUserByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException(String.format("User with email %s already exists",
                            userRegistrationRequestDto.getEmail()));
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setRoles(roleRepository.getAllByNameIn(Set.of(RoleName.ROLE_USER)));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
