package com.library.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate reserveDate;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}