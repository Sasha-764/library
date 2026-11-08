package com.library.library.repository;

import com.library.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library.entity.Role;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
}