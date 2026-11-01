package com.library.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Поле почта не заполнено")
        @Email(message = "Неправильный формат почты")
        String email,
        @NotBlank(message = "Поле пароль не заполнено")
        String password,
        @NotBlank(message = "Поле ФИО не заполнено")
        String fullName) {
}