package com.library.library.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Поле почта не заполнено") String email,
        @NotBlank(message = "Поле пароль не заполнено") String password) {
}