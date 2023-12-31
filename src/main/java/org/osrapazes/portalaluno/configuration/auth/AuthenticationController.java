package org.osrapazes.portalaluno.configuration.auth;

import org.osrapazes.portalaluno.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final EmailService emailService;
	private final AuthenticationService authenticationService;

	//controle de gerenciamento de login/registro
	//endpoints nao relacionados diretamente com autenticacao nao devem ser adicionados
	@PostMapping("/register/student")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestStudent request) {
		try {
			if (!isValidEmail(request.getEmail())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email inválido");
			}

			AuthenticationResponse response = authenticationService.register(request);
			emailService.sendConfirmationEmail(request.getEmail(), request.getName());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro no registro do aluno", e);
		}
	}

	@PostMapping("/register/admin")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestAdmin request) {
		return ResponseEntity.ok(authenticationService.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";

		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}