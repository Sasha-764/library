package com.library.library.security;

import com.library.library.entity.Role;
import com.library.library.entity.User;
import com.library.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User admin = userRepository.findByEmail("admin@library.com").orElseGet(() -> {
            User u = new User();
            u.setEmail("admin@library.com");
            u.setPassword(passwordEncoder.encode("admin123"));
            u.setFullName("Администратор");
            return u;
        });
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);
    }
}