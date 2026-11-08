package com.library.library.dto;

import com.library.library.entity.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
        @NotNull(message = "Поле роль не заполнено")
        Role role) {}
