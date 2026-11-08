package com.library.library.controller;

import com.library.library.dto.LoanRequest;
import com.library.library.dto.LoanResponse;
import com.library.library.entity.LoanStatus;
import com.library.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse reserve(@Valid @RequestBody LoanRequest request,
                                Authentication authentication) {
        return loanService.reserve(authentication.getName(), request.bookId());
    }

    @GetMapping("/my")
    public List<LoanResponse> my(Authentication authentication) {
        return loanService.getMy(authentication.getName());
    }

    @GetMapping
    public List<LoanResponse> all(@RequestParam(required = false) LoanStatus status) {
        return loanService.getAll(status);
    }

    @PatchMapping("/{id}/issue")
    public LoanResponse issue(@PathVariable Long id) {
        return loanService.issue(id);
    }

    @PatchMapping("/{id}/return")
    public LoanResponse returnBook(@PathVariable Long id) {
        return loanService.returnBook(id);
    }
}