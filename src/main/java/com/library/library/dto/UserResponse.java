package com.library.library.dto;

import com.library.library.entity.Role;
import com.library.library.entity.User;

public record UserResponse(Long id, String email, String fullName, Role role) {
    public static UserResponse of(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getFullName(), u.getRole());
    }
}