package com.dinobank.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordHasher {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordHasher() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
