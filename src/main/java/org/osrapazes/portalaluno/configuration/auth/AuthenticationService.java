package org.osrapazes.portalaluno.configuration.auth;

import java.util.Optional;

import org.osrapazes.portalaluno.configuration.JwtService;
import org.osrapazes.portalaluno.repositories.AdminRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.osrapazes.portalaluno.models.Admin;
import org.osrapazes.portalaluno.models.RoleEnum;
import org.osrapazes.portalaluno.models.Student;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;


	public AuthenticationResponse register(RegisterRequestAdmin request) {
		Admin user = Admin.builder()
			.name(request.getName())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(RoleEnum.ADMIN)
			.status(true)
			.build();
		adminRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}
	public AuthenticationResponse register(RegisterRequestStudent request) {
		Student user = Student.builder()
			.name(request.getName())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.cpf(request.getCpf())
			.rg(request.getRg())
			.role(RoleEnum.STUDENT)
			.status(true)
			.build();
		studentRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = studentRepository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();	


	}

}