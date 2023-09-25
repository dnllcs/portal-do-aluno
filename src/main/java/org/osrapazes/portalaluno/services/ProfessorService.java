package org.osrapazes.portalaluno.services;

import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.Subject;
import org.osrapazes.portalaluno.models.ProfessorResponse;
import org.osrapazes.portalaluno.repositories.ProfessorRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfessorService {

	private final ProfessorRepository professorRepository;

	public ResponseEntity<?> getAllProfessors() {
		return ResponseEntity.accepted().body(professorRepository.findAll());
	}

	public ResponseEntity<?> getAllProfessorsByStudent(Long id) {
		return ResponseEntity.accepted().body(professorRepository.findByStudentId(id));
	}

}