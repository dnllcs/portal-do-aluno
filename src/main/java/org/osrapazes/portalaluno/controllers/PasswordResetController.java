package org.osrapazes.portalaluno.controllers;

import org.osrapazes.portalaluno.services.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/password-reset")
public class PasswordResetController {

    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PasswordResetController(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/token/{id}")
    public String generatePasswordResetToken(@PathVariable Long id) {
        return passwordResetTokenService.generatePasswordResetToken(id);
    }

    @PostMapping("/reset/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody String newPassword) {
        try {
            String hashedPassword = passwordEncoder.encode(newPassword);
            passwordResetTokenService.resetPassword(token, hashedPassword);
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao redefinir a senha: " + e.getMessage());
        }
    }
}