package com.library.library.repository;

import com.library.library.entity.Book;
import com.library.library.entity.Loan;
import com.library.library.entity.LoanStatus;
import com.library.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserOrderByIdDesc(User user);
    List<Loan> findAllByOrderByIdDesc();
    List<Loan> findByStatusOrderByIdDesc(LoanStatus status);
    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDate date);
    boolean existsByUserAndBookAndStatusIn(User user, Book book, Collection<LoanStatus> statuses);
    boolean existsByBook(Book book);
    boolean existsByUser(User user);
}