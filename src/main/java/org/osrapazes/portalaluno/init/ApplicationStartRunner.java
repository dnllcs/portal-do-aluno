package org.osrapazes.portalaluno.init;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.osrapazes.portalaluno.configuration.auth.AuthenticationService;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestAdmin;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestStudent;
import org.osrapazes.portalaluno.models.RoleEnum;
import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//Cria na inicializacao da aplicacao um registro de aluno(email:alunoTeste@gmail.com senha:123) 
//e um registro de admin(email:adminTeste@gmail.com senha:123)
@Component
public class ApplicationStartRunner implements CommandLineRunner {


	private final AuthenticationService authenticationService;
	private final SubjectRepository subjectRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;

	public ApplicationStartRunner(AuthenticationService authenticationService, SubjectRepository subjectRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
		this.authenticationService = authenticationService;
		this.subjectRepository = subjectRepository;
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String ...args) throws Exception {
		if(!authenticationService.isEmailInUse("alunoTeste@gmail") && !authenticationService.isEmailInUse("adminTeste@gmail.com")) {
			authenticationService.register(new RegisterRequestStudent("alunoTeste", "alunoTeste@gmail.com", "123", "cpf123", "rg123", true));
			authenticationService.register(new RegisterRequestAdmin("adminTeste", "adminTeste@gmail.com", "123", true));
		}
		Student std1 = Student.builder()
			.name("Student1")
			.email("Student1@email")
			.password(passwordEncoder.encode("password"))
			.cpf("cpf")
			.rg("rg")
			.role(RoleEnum.STUDENT)
			.status(true)
			.birthDate(LocalDate.of(2000, 5, 11))
			.subjects((Set) new HashSet<>())
			.build();
		Student std2 = Student.builder()
			.name("Student2")
			.email("Student2@email")
			.password(passwordEncoder.encode("password"))
			.cpf("cpf")
			.rg("rg")
			.role(RoleEnum.STUDENT)
			.status(true)
			.birthDate(LocalDate.of(2000, 1, 17))
			.subjects((Set) new HashSet<>())
			.build();
		Student std3 = Student.builder()
			.name("Student3")
			.email("Student3@email")
			.password(passwordEncoder.encode("password"))
			.cpf("cpf")
			.rg("rg")
			.role(RoleEnum.STUDENT)
			.status(true)
			.birthDate(LocalDate.of(2000, 7, 15))
			.subjects((Set) new HashSet<>())
			.build();

		Subject sub1 = Subject.builder()
			.name("Subject1")
			.professor("professor1")
			.students((Set) new HashSet<>())
			.build();
		Subject sub2 = Subject.builder()
			.name("Subject2")
			.professor("professor2")
			.students((Set) new HashSet<>())
			.build();
		Subject sub3 = Subject.builder()
			.name("Subject3")
			.professor("professor3")
			.students((Set) new HashSet<>())
			.build();

		std1.addSubject(sub1);
		std1.addSubject(sub2);
		std1.addSubject(sub3);
		std2.addSubject(sub1);
		std2.addSubject(sub3);
		std3.addSubject(sub2);
		
		subjectRepository.save(sub1);
		subjectRepository.save(sub2);
		subjectRepository.save(sub3);

		studentRepository.save(std1);
		studentRepository.save(std2);
		studentRepository.save(std3);
	}
	
}