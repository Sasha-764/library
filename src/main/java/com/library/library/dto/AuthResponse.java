package com.library.library.dto;

import com.library.library.entity.Role;

public record AuthResponse(String accessToken, String email, String fullName, Role role) {
}