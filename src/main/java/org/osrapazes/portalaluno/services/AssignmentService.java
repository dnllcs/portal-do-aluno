package org.osrapazes.portalaluno.services;

import java.util.List;
import java.util.Optional;

import org.osrapazes.portalaluno.models.Assignment;
import org.osrapazes.portalaluno.models.AssignmentRequest;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.AssignmentResponse;
import org.osrapazes.portalaluno.repositories.AssignmentRepository;
import org.osrapazes.portalaluno.repositories.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssignmentService {


	private final AssignmentRepository assignmentRepository;
	private final SubjectRepository subjectRepository;
	private final StudentRepository studentRepository;

	public List<Assignment> getAllAssignments() {
		return assignmentRepository.findAll();
	}

	public List<AssignmentResponse> getAssignmentsBySubjectId(Long id) {
		Subject subject = subjectRepository.getById(id);
		return subject.getAssignments().stream().map(AssignmentResponse::new).toList();
	}

	public ResponseEntity<?> addAssignmentToSubjectById(Long subjectId, AssignmentRequest request) {
		Optional<Subject> subjectOptional = subjectRepository.findByIdEagerly(Long.valueOf(subjectId));
		Assignment assignment = Assignment.builder()
			.title(request.title())
			.description(request.description())
			.startDate(request.startDate())
			.endDate(request.endDate())
			.build();
		if(subjectOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("subject id:" + subjectId + " not found");
		}
		Subject subject = subjectOptional.get();
		subject.addAssignment(assignment);
		assignmentRepository.save(assignment);
		subjectRepository.save(subject);
		return ResponseEntity.accepted().body(subject.getAssignments().stream().map(AssignmentResponse::new).toList());
	}

	public ResponseEntity<?> removeAssignmentInSubjectById(Long subjectId, Long assignmentId) {
		Optional<Subject> subjectOptional = subjectRepository.findByIdEagerly(Long.valueOf(subjectId));
		Optional<Assignment> assignmentOptional = assignmentRepository.findById(Long.valueOf(assignmentId));
		if(subjectOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("subject id:" + subjectId + " not found");
		}
		else if(assignmentOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("assignment id :" + assignmentId + " not found");			
		}

		Subject subject = subjectOptional.get();
		Assignment assignment = assignmentOptional.get();
		subject.removeAssignment(assignment);
		assignmentRepository.delete(assignment);
		subjectRepository.save(subject);
		return ResponseEntity.accepted().body(subject.getAssignments().stream().map(AssignmentResponse::new).toList());
	}

	public ResponseEntity<?> removeAssignmentInSubjectByTitle(Long subjectId, String title) {
		Optional<Subject> subjectOptional = subjectRepository.findByIdEagerly(Long.valueOf(subjectId));
		Optional<Assignment> assignmentOptional = assignmentRepository.findByTitle(title);
		if(subjectOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("subject id:" + subjectId + " not found");
		}
		else if(assignmentOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("assignment title :" + title + " not found");			
		}

		Subject subject = subjectOptional.get();
		Assignment assignment = assignmentOptional.get();
		subject.removeAssignment(assignment);
		assignmentRepository.delete(assignment);
		subjectRepository.save(subject);
		return ResponseEntity.accepted().body(subject.getAssignments().stream().map(AssignmentResponse::new).toList());
	}

	public List<AssignmentResponse> getStudentAssignments(Long studentId) {
		return studentRepository
			.findById(Long.valueOf(studentId))
			.get()
			.getSubjects()
			.stream()
			.flatMap(sub -> sub.getAssignments().stream())
			.map(AssignmentResponse::new)
			.toList();
	}
}