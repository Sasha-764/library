package com.library.library.service;

import com.library.library.dto.CreateUserRequest;
import com.library.library.dto.UserResponse;
import com.library.library.entity.Role;
import com.library.library.entity.User;
import com.library.library.exception.ConflictException;
import com.library.library.exception.NotFoundException;
import com.library.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(CreateUserRequest r) {
        if (userRepository.existsByEmail(r.email())) {
            throw new ConflictException("Данная почта уже используется");
        }
        User u = new User();
        u.setEmail(r.email());
        u.setPassword(passwordEncoder.encode(r.password()));
        u.setFullName(r.fullName());
        u.setRole(r.role());
        return UserResponse.of(userRepository.save(u));
    }

    public List<UserResponse> list(Role role) {
        List<User> users = (role == null) ? userRepository.findAll() : userRepository.findByRole(role);
        return users.stream().map(UserResponse::of).toList();
    }

    public UserResponse changeRole(Long id, Role role, String currentEmail) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (u.getEmail().equals(currentEmail)) {
            throw new ConflictException("Нельзя изменить собственную роль");
        }
        u.setRole(role);
        return UserResponse.of(userRepository.save(u));
    }

    public void delete(Long id, String currentEmail) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (u.getEmail().equals(currentEmail)) {
            throw new ConflictException("Нельзя удалить самого себя");
        }
        userRepository.delete(u);
    }
}