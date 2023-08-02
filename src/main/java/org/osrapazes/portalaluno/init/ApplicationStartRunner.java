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
import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.Assignment;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.models.Assignment;
import org.osrapazes.portalaluno.models.AssignmentResponse;
import org.osrapazes.portalaluno.models.AssignmentRequest;
import org.osrapazes.portalaluno.models.Enrollment;
import org.osrapazes.portalaluno.repositories.EnrollmentRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.osrapazes.portalaluno.repositories.AssignmentRepository;
import org.osrapazes.portalaluno.services.PdfGeneratorService;
import org.osrapazes.portalaluno.services.SubjectService;
import org.osrapazes.portalaluno.services.AssignmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
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
				
		Subject subject1 = Subject.builder()
			.name("Subject - 1")
			.professor("Professor - 1")
			.students((Set) new HashSet<>())
			.build();
		Subject subject2 = Subject.builder()
			.name("Subject - 2")
			.professor("Professor - 2")
			.students((Set) new HashSet<>())
			.build();
		Subject subject3 = Subject.builder()
			.name("Subject - 3")
			.professor("Professor - 3")
			.students((Set) new HashSet<>())
			.build();
		Subject subject4 = Subject.builder()
			.name("Subject - 4")
			.professor("Professor - 4")
			.students((Set) new HashSet<>())
			.build();
		Subject subject5 = Subject.builder()
			.name("Subject - 5")
			.professor("Professor - 5")
			.students((Set) new HashSet<>())
			.build();
		Subject subject6 = Subject.builder()
			.name("Subject - 6")
			.professor("Professor - 6")
			.students((Set) new HashSet<>())
			.build();
		List<Subject> subjectList = new ArrayList<>();

		subjectList.addAll(Arrays.asList(subject1, subject2, subject3, subject4, subject5, subject6));

		enrollmentRepository.saveAll(Arrays.asList(enrollment1, enrollment2, enrollment3));

		authenticationService.register(studentRequest1);
		authenticationService.register(studentRequest2);
		authenticationService.register(studentRequest3);

		Student student1 = studentRepository.findByEmail(studentRequest1.getEmail()).get(); 
		Student student2 = studentRepository.findByEmail(studentRequest2.getEmail()).get(); 
		Student student3 = studentRepository.findByEmail(studentRequest3.getEmail()).get(); 
		
		subjectList.stream().forEach(sub -> subjectRepository.save(sub));

		subjectService.addSubjectsToStudent(student1.getStudentId(), subjectList.stream()
			.map(sub -> new SubjectRequest(sub.getName(), sub.getProfessor())).collect(Collectors.toList()));
		subjectService.addSubjectsToStudent(student2.getStudentId(), subjectList.stream()
			.map(sub -> new SubjectRequest(sub.getName(), sub.getProfessor())).collect(Collectors.toList()).subList(0, subjectList.size()/2));
		subjectService.addSubjectToStudent(student3.getStudentId(), new SubjectRequest(subject1.getName(), subject1.getProfessor()));
		pdfService.generateEnrollmentStatement(student1, "src/test/Comprovante de Matricula - "+ student1.getStudentId() +".pdf");

		AssignmentRequest assignmentRequest1 =  new AssignmentRequest("Assignment - 1", "assignment", LocalDateTime.of(2020, 10, 10, 10, 10), LocalDateTime.of(2020, 10, 10, 20, 20)); 
		AssignmentRequest assignmentRequest2 =  new AssignmentRequest("Assignment - 2", "assignment", LocalDateTime.of(2020, 10, 10, 10, 10), LocalDateTime.of(2020, 10, 10, 20, 20));
		AssignmentRequest assignmentRequest3 =  new AssignmentRequest("Assignment - 3", "assignment", LocalDateTime.of(2020, 10, 10, 10, 10), LocalDateTime.of(2020, 10, 10, 20, 20));
		AssignmentRequest assignmentRequest4 =  new AssignmentRequest("Assignment - 4", "assignment", LocalDateTime.of(2020, 10, 10, 10, 10), LocalDateTime.of(2020, 10, 10, 20, 20));
		AssignmentRequest assignmentRequest5 =  new AssignmentRequest("Assignment - 5", "assignment", LocalDateTime.of(2020, 10, 10, 10, 10), LocalDateTime.of(2020, 10, 10, 20, 20));


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
		assignmentService.addAssignmentToSubjectById(Long.valueOf(4), assignmentRequest4);		
		assignmentService.addAssignmentToSubjectById(Long.valueOf(4), assignmentRequest5);
		List<AssignmentResponse> listAssignment = assignmentRepository.findAllAssingmentsByStudentId(Long.valueOf(2))
			.stream()
			.map(AssignmentResponse::new)
			.collect(Collectors.toList());
	}
}
