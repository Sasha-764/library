package com.library.library.service;

import com.library.library.dto.LoanResponse;
import com.library.library.entity.Book;
import com.library.library.entity.Loan;
import com.library.library.entity.LoanStatus;
import com.library.library.entity.User;
import com.library.library.exception.ConflictException;
import com.library.library.exception.NotFoundException;
import com.library.library.exception.UnauthorizedException;
import com.library.library.repository.BookRepository;
import com.library.library.repository.LoanRepository;
import com.library.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private static final int LOAN_DAYS = 14;

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public LoanResponse reserve(String email, Long bookId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Пользователь не авторизован"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Книга не найдена"));

        List<LoanStatus> active = List.of(LoanStatus.RESERVED, LoanStatus.ISSUED, LoanStatus.OVERDUE);
        if (loanRepository.existsByUserAndBookAndStatusIn(user, book, active)) {
            throw new ConflictException("Эта книга уже забронирована или у вас на руках");
        }
        if (book.getAvailableCount() == null || book.getAvailableCount() < 1) {
            throw new ConflictException("Нет свободных экземпляров книги");
        }

        book.setAvailableCount(book.getAvailableCount() - 1);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setStatus(LoanStatus.RESERVED);
        loan.setReserveDate(LocalDate.now());
        return toResponse(loanRepository.save(loan));
    }

    @Transactional
    public List<LoanResponse> getMy(String email) {
        refreshOverdue();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Пользователь не авторизован"));
        return loanRepository.findByUserOrderByIdDesc(user).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public List<LoanResponse> getAll(LoanStatus status) {
        refreshOverdue();
        List<Loan> loans = (status == null)
                ? loanRepository.findAllByOrderByIdDesc()
                : loanRepository.findByStatusOrderByIdDesc(status);
        return loans.stream().map(this::toResponse).toList();
    }

    @Transactional
    public LoanResponse issue(Long id) {
        Loan loan = findLoan(id);
        if (loan.getStatus() != LoanStatus.RESERVED) {
            throw new ConflictException("Выдать можно только забронированную книгу");
        }
        loan.setStatus(LoanStatus.ISSUED);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_DAYS));
        return toResponse(loanRepository.save(loan));
    }

    @Transactional
    public LoanResponse returnBook(Long id) {
        Loan loan = findLoan(id);
        if (loan.getStatus() == LoanStatus.RESERVED) {
            throw new ConflictException("Книга ещё не выдана");
        }
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new ConflictException("Книга уже возвращена");
        }

        Book book = loan.getBook();
        book.setAvailableCount(book.getAvailableCount() + 1);
        bookRepository.save(book);

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        return toResponse(loanRepository.save(loan));
    }

    private Loan findLoan(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Выдача не найдена"));
    }

    private void refreshOverdue() {
        List<Loan> overdue = loanRepository
                .findByStatusAndDueDateBefore(LoanStatus.ISSUED, LocalDate.now());
        overdue.forEach(loan -> loan.setStatus(LoanStatus.OVERDUE));
        loanRepository.saveAll(overdue);
    }

    private LoanResponse toResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getUser().getId(),
                loan.getUser().getFullName(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getStatus(),
                loan.getReserveDate(),
                loan.getIssueDate(),
                loan.getDueDate(),
                loan.getReturnDate());
    }
}