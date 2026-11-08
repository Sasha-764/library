package com.library.library.controller;

import com.library.library.dto.ChangeRoleRequest;
import com.library.library.dto.CreateUserRequest;
import com.library.library.dto.UserResponse;
import com.library.library.entity.Role;
import com.library.library.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest r) {
        return service.create(r);
    }

    @GetMapping
    public List<UserResponse> list(@RequestParam(required = false) Role role) {
        return service.list(role);
    }

    @PatchMapping("/{id}/role")
    public UserResponse changeRole(@PathVariable Long id, @Valid @RequestBody ChangeRoleRequest r,
                                   Authentication auth) {
        return service.changeRole(id, r.role(), auth.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, Authentication auth) {
        service.delete(id, auth.getName());
    }
}