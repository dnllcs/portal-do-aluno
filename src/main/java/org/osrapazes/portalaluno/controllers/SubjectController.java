package org.osrapazes.portalaluno.controllers;

import java.util.List;
import java.util.Arrays;

import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.SubjectRequest;
import org.osrapazes.portalaluno.services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//Controle apenas utilizado para testes
@RestController
@RequestMapping("v1/subjects")
public class SubjectController {

	private final SubjectService subjectService;

	public SubjectController(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	@GetMapping("")
	public ResponseEntity<?> getAllSubjects() {
		return subjectService.getAllSubjects();
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<?> getSubjectsById(@PathVariable("id") int id) {
		return subjectService.getSubjectsById(Long.valueOf(id));
	}

	@PostMapping("/student/{id}/add")
	public ResponseEntity<?> addSubjectToStudent(@PathVariable("id") int id, @RequestBody SubjectRequest request) {
		return subjectService.addSubjectToStudent(Long.valueOf(id), request);
	}

	@PostMapping(value = "/student/{id}/add-all")
	public ResponseEntity<?> addSubjectsToStudent(@PathVariable("id") int id, @RequestBody SubjectRequest[] request) {
		return subjectService.addSubjectsToStudent(Long.valueOf(id), Arrays.asList(request));
	}

	@PostMapping("/student/{id}/remove")
	public ResponseEntity<?> removeSubjectToStudent(@PathVariable("id") int id, @RequestBody SubjectRequest request) {
		return subjectService.removeSubjectToStudent(Long.valueOf(id), request);				
	}

}