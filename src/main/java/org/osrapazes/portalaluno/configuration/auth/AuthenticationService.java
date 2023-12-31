package org.osrapazes.portalaluno.configuration.auth;

import org.osrapazes.portalaluno.configuration.JwtService;
import org.osrapazes.portalaluno.repositories.AdminRepository;
import org.osrapazes.portalaluno.repositories.EnrollmentRepository;
import org.osrapazes.portalaluno.repositories.ProfessorRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.osrapazes.portalaluno.models.Admin;
import org.osrapazes.portalaluno.models.Enrollment;
import org.osrapazes.portalaluno.models.RoleEnum;
import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.User;
import org.osrapazes.portalaluno.models.Professor;

import jakarta.persistence.EntityExistsException;
//Service responsavel por processar requests dos endpoints do AuthenticationController
@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final ProfessorRepository professorRepository;

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
		Enrollment enrollment = enrollmentRepository.findByEnrollmentCode(request.getEnrollmentCode()).orElseThrow();
		if(userRepository.findByEmailAll(request.getEmail()).isPresent()) {
			throw new EntityExistsException("Email already registered");
		}
		else if(enrollment.getStudent() != null) {
			throw new EntityExistsException("Enrollment code already in use");
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
		student.addEnrollment(enrollment);
		studentRepository.save(student);
		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
			.token(jwtToken)
			.build();
	}	

	public AuthenticationResponse register(RegisterRequestProfessor request) throws EntityExistsException {
		if(userRepository.findByEmailAll(request.getEmail()).isPresent()) {
			throw new EntityExistsException("Email already registered");
		}

		Professor professor = Professor.builder()
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


		professor.addUser(user);
		professorRepository.save(professor);
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