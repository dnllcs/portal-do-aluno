package org.osrapazes.portalaluno.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.StudentResponseDTO;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubjectService {

	private final StudentRepository studentRepository;
	private final SubjectRepository subjectRepository;

	public List<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

	public List<Subject> getSubjects(Long id) {
		Student std = studentRepository.getById(id);
		return std.getSubjectsAsList();
	}

	public ResponseEntity<?> addSubjectToStudent(Long id, SubjectRequest request) {
		Optional<Student> studentOptional = studentRepository.findByIdEagerly(Long.valueOf(id));
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
		return ResponseEntity.accepted().body(new StudentResponseDTO(student.getStudentId(), student.getName(), null, null, null, student.getSubjectsAsList()));
	}

	public ResponseEntity<?> addSubjectsToStudent(Long id, List<SubjectRequest> request) {
		Optional<Student> studentOptional = studentRepository.findByIdEagerly(Long.valueOf(id));
		List<Subject> subjects = request.stream().map(req -> subjectRepository.
			findByNameAndProfessor(req.name(), req.professor())
			.orElseThrow(() -> new EntityNotFoundException("Subject:" + req.name() + " professor: " + req.professor() + " not found")))
			.collect(Collectors.toList());
		if(studentOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student id:" + id + " not found");
		}
		Student student = studentOptional.get();
		subjects.stream().forEach(sub -> student.addSubject(sub));
		studentRepository.save(student);
		return ResponseEntity.accepted().body(new StudentResponseDTO(student.getStudentId(), student.getName(), null, null, null, student.getSubjectsAsList()));
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
		return ResponseEntity.accepted().body(new StudentResponseDTO(student.getStudentId(), student.getName(), null, null, null, student.getSubjectsAsList()));
	}


}