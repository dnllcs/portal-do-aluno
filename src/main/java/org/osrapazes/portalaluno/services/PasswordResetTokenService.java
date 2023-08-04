package org.osrapazes.portalaluno.services;

import org.osrapazes.portalaluno.configuration.JwtService;
import org.osrapazes.portalaluno.models.PasswordResetToken;
import org.osrapazes.portalaluno.models.User;
import org.osrapazes.portalaluno.repositories.PasswordResetTokenRepository;
import org.osrapazes.portalaluno.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordResetTokenService {

    private static final int EXPIRATION_IN_MINUTES = 60 * 30;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    public String generatePasswordResetToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        if (!user.isEnabled()) {
            throw new RuntimeException("Usuário não está ativo.");
        }
        Date now = new Date();
        Date expiryDate = calculateExpiryDate(now);

        String token = jwtService.generateToken(user);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetToken.setUserId(user.getUserId());

        passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return token;
    }


    private Date calculateExpiryDate(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, EXPIRATION_IN_MINUTES);
        return calendar.getTime();
    }


    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || isTokenExpired(passwordResetToken)) {
            throw new RuntimeException("Token inválido ou expirado.");
        }

        User user = userRepository.findById(passwordResetToken.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        user.setPassword(newPassword);
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);
    }

    private boolean isTokenExpired(PasswordResetToken passwordResetToken) {
        return passwordResetToken.getExpiryDate().before(new Date());
    }
}