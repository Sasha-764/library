package com.library.library.dto;

import com.library.library.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank(message = "Поле почта не заполнено")
        @Email(message = "Неправильный формат почты")
        String email,

        @NotBlank(message = "Поле пароль не заполнено")
        String password,

        String fullName,

        @NotNull(message = "Поле роль не заполнено")
        Role role) {}