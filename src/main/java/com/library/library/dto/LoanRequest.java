package com.library.library.dto;

import jakarta.validation.constraints.NotNull;

public record LoanRequest(
        @NotNull(message = "Поле книга не заполнено") Long bookId) {
}