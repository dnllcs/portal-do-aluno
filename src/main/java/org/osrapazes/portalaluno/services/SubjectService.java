package org.osrapazes.portalaluno.services;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

	private final StudentRepository studentRepository;

	private final SubjectRepository subjectRepository;

	public SubjectService(StudentRepository studentRepository, SubjectRepository subjectRepository) {
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
	}

	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

	public List<Subject> getSubjects(Long id) {
		Student std = studentRepository.getById(id);
		return std.getSubjects();
	}

	public ResponseEntity<?> addSubjectToStudent(Long id, SubjectRequest request) {
		Optional<Student> studentOptional = studentRepository.findById(Long.valueOf(id));
		Optional<Subject> subjectOptional = subjectRepository.findByNameAndProfessor(request.name(), request.professor());
		if(studentOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student id:" + id + " not found");
		}
		else if(subjectOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject name: " + request.name() + " professor: " + request.professor() + " not found");	
		}
		Student student = studentOptional.get();
		student.addSubject(subjectOptional.get());
		studentRepository.save(student);
		return ResponseEntity.accepted().body(student);
	}

	public ResponseEntity<?> removeSubjectToStudent(Long id, SubjectRequest request) {
		Optional<Student> studentOptional = studentRepository.findById(Long.valueOf(id));
		Optional<Subject> subjectOptional = subjectRepository.findByNameAndProfessor(request.name(), request.professor());
		if(studentOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student id:" + id + " not found");
		}
		else if(subjectOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject name: " + request.name() + " professor: " + request.professor() + " not found");	
		}
		Student student = studentOptional.get();
		student.removeSubject(subjectOptional.get());
		studentRepository.save(student);
		return ResponseEntity.accepted().body(student);
	}


}