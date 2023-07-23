package org.osrapazes.portalaluno.controllers;

import java.util.List;

import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//Controle apenas utilizado para testes
@RestController
@RequestMapping("api/v1/demo")
public class SubjectController {

	private final StudentRepository studentRepository;
	private final SubjectRepository subjectRepository;

	public SubjectController(StudentRepository studentRepository, SubjectRepository subjectRepository) {
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
	}

	@GetMapping("/get")
	public ResponseEntity<String> returnString() {
		return ResponseEntity.ok("TextTextTextTextText");
	}


	@GetMapping("/getStudentSubjects/{id}")
	public List<Subject> getSubjects(@PathVariable("id") int id) {
		Student std = studentRepository.getById((long) id);
		System.out.println(std + "      " + id);
		return std.getSubjects();
	}

	@PostMapping("/addSubjectToStudent/{id}")
	public ResponseEntity<String> addSubjectToStudent(@PathVariable("id") int id, @RequestBody SubjectRequest request) {
		System.out.println("++++++++++++++++" + request.toString() + "++++++++++++++++");
		Student student = studentRepository.findById(Long.valueOf(id)).orElseThrow();
		student.addSubject(subjectRepository.findByNameAndProfessor(request.name(), request.professor()).get());
		studentRepository.save(student);
		return ResponseEntity.ok("ok");
	}
	@PostMapping("/removeSubjectToStudent/{id}")
		public ResponseEntity<String> removeSubjectToStudent(@PathVariable("id") int id, @RequestBody SubjectRequest request) {
			System.out.println("++++++++++++++++" + request.toString() + "++++++++++++++++");
			Student student = studentRepository.findById(Long.valueOf(id)).orElseThrow();
			student.removeSubject(subjectRepository.findByNameAndProfessor(request.name(), request.professor()).get());
			studentRepository.save(student);
			return ResponseEntity.ok("ok");
		}



}