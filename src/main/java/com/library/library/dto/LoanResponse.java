package com.library.library.dto;

import com.library.library.entity.LoanStatus;

import java.time.LocalDate;

public record LoanResponse(
        Long id,
        Long userId,
        String fullName,
        Long bookId,
        String bookTitle,
        LoanStatus status,
        LocalDate reserveDate,
        LocalDate issueDate,
        LocalDate dueDate,
        LocalDate returnDate) {
}