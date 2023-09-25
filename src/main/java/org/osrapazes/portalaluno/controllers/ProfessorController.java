package org.osrapazes.portalaluno.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.osrapazes.portalaluno.services.ProfessorService;


@RequestMapping("v1/professor")
@RestController
@RequiredArgsConstructor
public class ProfessorController {

	private final ProfessorService professorService;

	@GetMapping("")
	public ResponseEntity<?> getAllProfessors() {
		return professorService.getAllProfessors();
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<?> getAllProfessorsByStudentId(@PathVariable int id) {
		return professorService.getAllProfessorsByStudent(Long.valueOf(id));
	}
}