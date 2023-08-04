package org.osrapazes.portalaluno.models;

import java.util.List;
import java.util.Arrays;

import org.osrapazes.portalaluno.models.AssignmentResponse;

public record SubjectResponse(Long id, String nome, String professor, List<AssignmentResponse> assignments) {
	public SubjectResponse(Subject subject) {
		this(subject.getSubjectId(), subject.getName(), subject.getProfessor().getName(), subject.getAssignments().stream().map(AssignmentResponse::new).toList());
	}
}