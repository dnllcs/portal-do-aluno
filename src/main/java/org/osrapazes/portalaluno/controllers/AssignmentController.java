package org.osrapazes.portalaluno.controllers;

import java.util.List;
import java.util.Arrays;

import org.osrapazes.portalaluno.models.Assignment;
import org.osrapazes.portalaluno.models.AssignmentRequest;
import org.osrapazes.portalaluno.models.AssignmentResponse;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.services.AssignmentService;
import org.osrapazes.portalaluno.services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

//Controle apenas utilizado para testes
@RestController
@RequestMapping("v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

	private final AssignmentService assignmentService;

	@GetMapping("")
	public List<Assignment> getAllAssignments() {
		return assignmentService.getAllAssignments();
	}

	@GetMapping("subject/{id}")
	public List<AssignmentResponse> getAssignmentsBySubjectId(@PathVariable("id") int id) {
		return assignmentService.getAssignmentsBySubjectId(Long.valueOf(id));
	}

	@PostMapping("/subject/{id}/add")
	public ResponseEntity<?> addAssignmentToSubject(@PathVariable("id") int id, @RequestBody AssignmentRequest request) {
		return assignmentService.addAssignmentToSubjectById(Long.valueOf(id), request);
	}

	@GetMapping("/student/{id}")
	public List<AssignmentResponse> getStudentAssignments(@PathVariable("id") int id) {
		return assignmentService.getStudentAssignments(Long.valueOf(id));
	}

	@PostMapping("/subject/{id}/remove-id")
	public ResponseEntity<?> removeAssignmentInSubjectById(@PathVariable("id") int subjectId, @RequestBody int assignmentId) {
		return assignmentService.removeAssignmentInSubjectById(Long.valueOf(subjectId), Long.valueOf(assignmentId));				
	}

	@PostMapping("/subject/{id}/remove-title")
	public ResponseEntity<?> removeAssignmentInSubjectByTitle(@PathVariable("id") int id, @RequestBody String title) {
		return assignmentService.removeAssignmentInSubjectByTitle(Long.valueOf(id), title);				
	}
}