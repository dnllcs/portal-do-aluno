package org.osrapazes.portalaluno.configuration.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


	private final AuthenticationService authenticationService;


	//controle de gerenciamento de login/registro
	//endpoints nao relacionados diretamente com autenticacao nao devem ser adicionados
	@PostMapping("/registerStudent")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestStudent request) {
		return ResponseEntity.ok(authenticationService.register(request));
	}
	@PostMapping("/registerAdmin")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestAdmin request) {
		return ResponseEntity.ok(authenticationService.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}