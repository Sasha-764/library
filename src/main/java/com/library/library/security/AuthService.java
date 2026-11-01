package com.library.library.service;

import com.library.library.dto.AuthResponse;
import com.library.library.dto.LoginRequest;
import com.library.library.dto.RegisterRequest;
import com.library.library.entity.Role;
import com.library.library.entity.User;
import com.library.library.exception.ConflictException;
import com.library.library.exception.UnauthorizedException;
import com.library.library.repository.UserRepository;
import com.library.library.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Данная почта уже используется");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setRole(Role.READER);
        userRepository.save(user);
        return toResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Неверная почта или пароль"));
        return toResponse(user);
    }

    private AuthResponse toResponse(User user) {
        return new AuthResponse(
                jwtService.generateToken(user),
                user.getEmail(),
                user.getFullName(),
                user.getRole());
    }
}