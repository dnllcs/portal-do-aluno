package org.osrapazes.portalaluno.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendConfirmationEmail(String destination, String name) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailUsername);
            helper.setTo(destination);
            helper.setSubject("Bem-vindo ao Portal do Aluno UniFatecie!");

            String htmlBody = "<h3>A UniFatecie lhe dá as boas vindas ao nosso sitema integrado de alunos</h3>"
                    + "<p>Boas vindas, " + name + "!\n\nSeu cadastro no Portal do Aluno UniFatecie foi realizado com sucesso.</p>"
                    + "<img src='cid:logo'>";

            helper.setText(htmlBody, true);

            FileSystemResource image = new FileSystemResource("src/main/resources/images/logo.png");
            helper.addInline("logo", image);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }
}
