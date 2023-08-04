package org.osrapazes.portalaluno.models;

import java.time.LocalDateTime;

public record AssignmentResponse(Long id, String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
	public AssignmentResponse(Assignment assignment) {
		this(assignment.getAssignmentId(), assignment.getTitle(), assignment.getDescription(), assignment.getStartDate(), assignment.getEndDate());
	}
}