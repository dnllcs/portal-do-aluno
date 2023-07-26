package org.osrapazes.portalaluno.configuration.auth;

import java.util.Optional;

import org.osrapazes.portalaluno.configuration.JwtService;
import org.osrapazes.portalaluno.repositories.AdminRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.osrapazes.portalaluno.models.Admin;
import org.osrapazes.portalaluno.models.RoleEnum;
import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.User;

import jakarta.persistence.EntityExistsException;
//Service responsavel por processar requests dos endpoints do AuthenticationController
@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;


	public AuthenticationResponse register(RegisterRequestAdmin request) throws EntityExistsException {
		if(userRepository.findByEmailAll(request.getEmail()).isPresent()) {
			throw new EntityExistsException("Email already registered");
		}
		Admin admin = Admin.builder()
			.name(request.getName())
			.email(request.getEmail())
			.status(true)
			.build();

		User user = User.builder()
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(RoleEnum.ADMIN)
			.build();

		admin.addUser(user);
		adminRepository.save(admin);
		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}
	public AuthenticationResponse register(RegisterRequestStudent request) throws EntityExistsException {
		if(userRepository.findByEmailAll(request.getEmail()).isPresent()) {
			throw new EntityExistsException("Email already registered");
		}
		Student student = Student.builder()
			.name(request.getName())
			.email(request.getEmail())
			.cpf(request.getCpf())
			.rg(request.getRg())
			.status(true)
			.build();

		User user = User.builder()
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(RoleEnum.STUDENT)
			.build();

		student.addUser(user);
		studentRepository.save(student);
		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}	

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findByEmailAll(request.getEmail()).get();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();	
	}

}