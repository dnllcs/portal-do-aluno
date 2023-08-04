package org.osrapazes.portalaluno.models;

import java.util.List;

public record ProfessorResponse(Long id, String name, String email) {

	public ProfessorResponse(Professor professor) {
		this(professor.getProfessorId(), professor.getName(), professor.getUser().getEmail());
	}
}