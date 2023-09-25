package org.osrapazes.portalaluno.init;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import org.osrapazes.portalaluno.configuration.auth.AuthenticationService;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestAdmin;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestStudent;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestProfessor;
import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.Assignment;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.models.Assignment;
import org.osrapazes.portalaluno.models.AssignmentResponse;
import org.osrapazes.portalaluno.models.AssignmentRequest;
import org.osrapazes.portalaluno.models.Enrollment;
import org.osrapazes.portalaluno.models.Professor;
import org.osrapazes.portalaluno.repositories.EnrollmentRepository;
import org.osrapazes.portalaluno.repositories.ProfessorRepository;
import org.osrapazes.portalaluno.repositories.PasswordResetTokenRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.osrapazes.portalaluno.repositories.AssignmentRepository;
import org.osrapazes.portalaluno.services.PdfGeneratorService;
import org.osrapazes.portalaluno.services.SubjectService;
import org.osrapazes.portalaluno.services.AssignmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

//Cria na inicializacao da aplicacao um registro de aluno(email:alunoTeste@gmail.com senha:123) 
//e um registro de admin(email:adminTeste@gmail.com senha:123)
@Component
@RequiredArgsConstructor
@Profile("dev")
public class ApplicationStartRunner implements CommandLineRunner {

	private final AuthenticationService authenticationService;
	private final SubjectRepository subjectRepository;
	private final SubjectService subjectService;
	private final PdfGeneratorService pdfService;
	private final StudentRepository studentRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final AssignmentService assignmentService;
	private final AssignmentRepository assignmentRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProfessorRepository professorRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public void run(String ...args) throws Exception {
		Enrollment enr = Enrollment.builder()
			.enrollmentCode("321")
			.enrollmentDate(LocalDate.of(2020, 1, 1))
			.build();
		enrollmentRepository.save(enr);

		authenticationService.register(new RegisterRequestStudent("alunoTeste",
			"alunoTeste@gmail.com", "123", "cpf123", "rg123", LocalDate.of(1995, 10, 10), "321"));
		authenticationService.register(new RegisterRequestAdmin("adminTeste", "adminTeste@gmail.com", "123"));

		passwordResetTokenRepository.findAll();

		RegisterRequestStudent studentRequest1 = RegisterRequestStudent
			.builder()
			.name("Student - 1")
			.email("Student1@gmail.com")
			.password("123")
			.cpf("123.123")
			.rg("123-123")
			.birthDate(LocalDate.of(2000, 1, 10))
			.enrollmentCode("123")
			.build();
		RegisterRequestStudent studentRequest2 = RegisterRequestStudent
			.builder()
			.name("Student - 2")
			.email("Student2@gmail.com")
			.password("123")
			.cpf("123.123")
			.rg("123-123")
			.birthDate(LocalDate.of(2000, 2, 20))
			.enrollmentCode("234")
			.build();
		RegisterRequestStudent studentRequest3 = RegisterRequestStudent
			.builder()
			.name("Student - 3")
			.email("Student3@gmail.com")
			.password("123")
			.cpf("123.123")
			.rg("123-123")
			.birthDate(LocalDate.of(2000, 3, 30))
			.enrollmentCode("345")
			.build();

		Enrollment enrollment1 = Enrollment.builder()
			.enrollmentCode("123")
			.enrollmentDate(LocalDate.of(2020, 2, 2))
			.build();

		Enrollment enrollment2 = Enrollment.builder()
			.enrollmentCode("234")
			.enrollmentDate(LocalDate.of(2020, 4, 4))
			.build();
		
		Enrollment enrollment3 = Enrollment.builder()
			.enrollmentCode("345")
			.enrollmentDate(LocalDate.of(2020, 6, 6))
			.build();

		Enrollment enrollment4 = Enrollment.builder()
			.enrollmentCode("222")
			.enrollmentDate(LocalDate.of(2020, 2, 2))
			.build();
		Enrollment enrollment5 = Enrollment.builder()
			.enrollmentCode("333")
			.enrollmentDate(LocalDate.of(2020, 2, 2))
			.build();
		Enrollment enrollment6 = Enrollment.builder()
			.enrollmentCode("444")
			.enrollmentDate(LocalDate.of(2020, 2, 2))
			.build();

        RegisterRequestProfessor professorRequest1 = RegisterRequestProfessor.builder()
                .name("Mauricio")
                .email("professor1@example.com")
                .password("123")
                .cpf("123")
                .rg("123")
                .birthDate(LocalDate.of(1980, 5, 10))
                .build();
                
        RegisterRequestProfessor professorRequest2 = RegisterRequestProfessor.builder()
                .name("Marcos")
                .email("professor2@example.com")
                .password("123")
                .cpf("123")
                .rg("123")
                .birthDate(LocalDate.of(1975, 8, 10))
                .build();

        RegisterRequestProfessor professorRequest3 = RegisterRequestProfessor.builder()
                .name("Marcelo")
                .email("professor3@example.com")
                .password("123")
                .cpf("123")
                .rg("123")
                .birthDate(LocalDate.of(1990, 2, 10))
                .build();

        RegisterRequestProfessor professorRequest4 = RegisterRequestProfessor.builder()
                .name("Mauro")
                .email("professor4@example.com")
                .password("123")
                .cpf("123")
                .rg("123")
                .birthDate(LocalDate.of(1990, 5, 15))
                .build();

        RegisterRequestProfessor professorRequest5 = RegisterRequestProfessor.builder()
                .name("Matheus")
                .email("professor5@example.com")
                .password("123")
                .cpf("123")
                .rg("123")
                .birthDate(LocalDate.of(1990, 6, 16))
                .build();

        RegisterRequestProfessor professorRequest6 = RegisterRequestProfessor.builder()
                .name("Maycol")
                .email("professor6@example.com")
                .password("123")
                .cpf("123")
                .rg("123")
                .birthDate(LocalDate.of(1990, 11, 5))
                .build();


        authenticationService.register(professorRequest1);
        authenticationService.register(professorRequest2);
        authenticationService.register(professorRequest3);
        authenticationService.register(professorRequest4);
        authenticationService.register(professorRequest5);
        authenticationService.register(professorRequest6);


        Professor professor1 = professorRepository.findByEmail(professorRequest1.getEmail()).orElseThrow();
        Professor professor2 = professorRepository.findByEmail(professorRequest2.getEmail()).orElseThrow();
        Professor professor3 = professorRepository.findByEmail(professorRequest3.getEmail()).orElseThrow();
        Professor professor4 = professorRepository.findByEmail(professorRequest4.getEmail()).orElseThrow();
        Professor professor5 = professorRepository.findByEmail(professorRequest5.getEmail()).orElseThrow();
        Professor professor6 = professorRepository.findByEmail(professorRequest6.getEmail()).orElseThrow();


		Subject subject1 = Subject.builder()
			.name("Calculo I")
			.students((Set) new HashSet<>())
			.build();
		Subject subject2 = Subject.builder()
			.name("Banco de dados I")
			.students((Set) new HashSet<>())
			.build();
		Subject subject3 = Subject.builder()
			.name("Programacao a objetos")
			.students((Set) new HashSet<>())
			.build();
		Subject subject4 = Subject.builder()
			.name("Algoritimos e Estruturas de Dados")
			.students((Set) new HashSet<>())
			.build();
		Subject subject5 = Subject.builder()
			.name("Empreendedorismo")
			.students((Set) new HashSet<>())
			.build();
		Subject subject6 = Subject.builder()
			.name("Analise de Sistemas")
			.students((Set) new HashSet<>())
			.build();
		Subject subject7 = Subject.builder()
			.name("Projeto Integrador I")
			.students((Set) new HashSet<>())
			.build();
		Subject subject8 = Subject.builder()
			.name("Analise de Sistemas")
			.students((Set) new HashSet<>())
			.build();
		Subject subject9 = Subject.builder()
			.name("Arquitetura de computadores")
			.students((Set) new HashSet<>())
			.build();
		Subject subject10 = Subject.builder()
			.name("Ingles instrumental")
			.students((Set) new HashSet<>())
			.build();
		Subject subject11 = Subject.builder()
			.name("Etica")
			.students((Set) new HashSet<>())
			.build();
		Subject subject12 = Subject.builder()
			.name("Calculo II")
			.students((Set) new HashSet<>())
			.build();


		professor1.addSubject(subject1);
		professor1.addSubject(subject2);

		professor2.addSubject(subject3);
		professor2.addSubject(subject4);

		professor3.addSubject(subject5);
		professor3.addSubject(subject6);

		professor4.addSubject(subject7);
		professor4.addSubject(subject8);

		professor5.addSubject(subject9);
		professor5.addSubject(subject10);

		professor6.addSubject(subject11);
		professor6.addSubject(subject12);



		List<Subject> subjectList = new ArrayList<>();
		List<Professor> professorList = new ArrayList<>();


		subjectList.addAll(Arrays.asList(
			subject1, subject2, subject3, subject4, subject5, subject6,
			subject7,subject8, subject9, subject10, subject11, subject12));

		subjectList.stream().forEach(sub -> subjectRepository.save(sub));

		professorList.addAll(Arrays.asList(professor1, professor2, professor3, professor4, professor5, professor6));
		professorRepository.saveAll(professorList);
		
		enrollmentRepository.saveAll(Arrays.asList(enrollment1, enrollment2, enrollment3, enrollment4, enrollment5, enrollment6));

		authenticationService.register(studentRequest1);
		authenticationService.register(studentRequest2);
		authenticationService.register(studentRequest3);

		Student student1 = studentRepository.findByEmail(studentRequest1.getEmail()).get(); 
		Student student2 = studentRepository.findByEmail(studentRequest2.getEmail()).get(); 
		Student student3 = studentRepository.findByEmail(studentRequest3.getEmail()).get(); 

		subjectService.addSubjectsToStudent(student1.getStudentId(), subjectList.stream()
			.map(sub -> new SubjectRequest(sub.getName(), sub.getProfessor().getName())).collect(Collectors.toList()));
		subjectService.addSubjectsToStudent(student2.getStudentId(), subjectList.stream()
			.map(sub -> new SubjectRequest(sub.getName(), sub.getProfessor().getName())).collect(Collectors.toList()).subList(0, subjectList.size()/2));
		subjectService.addSubjectToStudent(student3.getStudentId(), new SubjectRequest(subject1.getName(), subject1.getProfessor().getName()));
		pdfService.generateEnrollmentStatement(student1, "src/test/Comprovante de Matricula - "+ student1.getStudentId() +".pdf");

		AssignmentRequest assignmentRequest1 =  new AssignmentRequest("Prova", "assignment", LocalDateTime.of(2023, 8, 2, 23, 59), LocalDateTime.of(2023, 8, 2, 23, 59)); 
		AssignmentRequest assignmentRequest2 =  new AssignmentRequest("Teste", "assignment", LocalDateTime.of(2023, 8, 4, 23, 59), LocalDateTime.of(2023, 8, 4, 23, 59));
		AssignmentRequest assignmentRequest3 =  new AssignmentRequest("Trabalho", "assignment", LocalDateTime.of(2023, 8, 6, 23, 59), LocalDateTime.of(2023, 8, 6, 23, 59));
		AssignmentRequest assignmentRequest4 =  new AssignmentRequest("Trabalho", "assignment", LocalDateTime.of(2023, 8, 8, 23, 59), LocalDateTime.of(2023, 8, 8, 23, 59));
		AssignmentRequest assignmentRequest5 =  new AssignmentRequest("Roteiro de seminario", "assignment", LocalDateTime.of(2023, 8, 10, 23, 59), LocalDateTime.of(2023, 8, 10, 23, 59));

		AssignmentRequest assignmentRequest6 =  new AssignmentRequest("Roteiro de seminario", "assignment", LocalDateTime.of(2023, 8, 12, 23, 59), LocalDateTime.of(2023, 8, 12, 23, 59));
		AssignmentRequest assignmentRequest7 =  new AssignmentRequest("Roteiro de seminario", "assignment", LocalDateTime.of(2023, 8, 14, 23, 59), LocalDateTime.of(2023, 8, 14, 23, 59));
		AssignmentRequest assignmentRequest8 =  new AssignmentRequest("Relatorio", "assignment", LocalDateTime.of(2023, 8, 15, 23, 59), LocalDateTime.of(2023, 8, 15, 23, 59));
		AssignmentRequest assignmentRequest9 =  new AssignmentRequest("Relatorio", "assignment", LocalDateTime.of(2023, 8, 16, 23, 59), LocalDateTime.of(2023, 8, 16, 23, 59));
		AssignmentRequest assignmentRequest10 =  new AssignmentRequest("Trabalho", "assignment", LocalDateTime.of(2023, 8, 17, 23, 59), LocalDateTime.of(2023, 8, 17, 23, 59));
		AssignmentRequest assignmentRequest11 =  new AssignmentRequest("Assignment", "assignment", LocalDateTime.of(2023, 8, 18, 23, 59), LocalDateTime.of(2023, 8, 18, 23, 59));
		AssignmentRequest assignmentRequest12 =   new AssignmentRequest("Prova", "assignment", LocalDateTime.of(2023, 8, 20, 23, 59), LocalDateTime.of(2023, 8, 20, 23, 59));
		AssignmentRequest assignmentRequest13 =  new AssignmentRequest("Prova", "assignment", LocalDateTime.of(2023, 8, 22, 23, 59), LocalDateTime.of(2023, 8, 22, 23, 59));
		AssignmentRequest assignmentRequest14 =  new AssignmentRequest("Assignment", "assignment", LocalDateTime.of(2023, 8, 23, 23, 59), LocalDateTime.of(2023, 8, 23, 23, 59));
		AssignmentRequest assignmentRequest15 =   new AssignmentRequest("Prova", "assignment", LocalDateTime.of(2023, 8, 24, 23, 59), LocalDateTime.of(2023, 8, 24, 23, 59));
		AssignmentRequest assignmentRequest16 =  new AssignmentRequest("Prova", "assignment", LocalDateTime.of(2023, 8, 25, 23, 59), LocalDateTime.of(2023, 8, 25, 23, 59));


		assignmentService.addAssignmentToSubjectById(Long.valueOf(2), assignmentRequest1);
		assignmentRepository.save(Assignment.builder()
			.title("title")
			.description("description")
			.subject(subject2)
			.startDate(LocalDateTime.of(2020, 10, 10, 10, 10))
			.endDate(LocalDateTime.of(2020, 10, 10, 20, 20))
			.build());

		assignmentService.addAssignmentToSubjectById(Long.valueOf(2), assignmentRequest2);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(2), assignmentRequest3);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(3), assignmentRequest3);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(3), assignmentRequest4);		
		assignmentService.addAssignmentToSubjectById(Long.valueOf(4), assignmentRequest5);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(5), assignmentRequest6);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(6), assignmentRequest7);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(6), assignmentRequest8);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(7), assignmentRequest9);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(8), assignmentRequest10);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(9), assignmentRequest11);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(9), assignmentRequest12);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(9), assignmentRequest13);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(10), assignmentRequest14);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(11), assignmentRequest15);
		assignmentService.addAssignmentToSubjectById(Long.valueOf(12), assignmentRequest16);


		List<AssignmentResponse> listAssignment = assignmentRepository.findAllAssingmentsByStudentId(Long.valueOf(2))
			.stream()
			.map(AssignmentResponse::new)
			.collect(Collectors.toList());
	}
}
