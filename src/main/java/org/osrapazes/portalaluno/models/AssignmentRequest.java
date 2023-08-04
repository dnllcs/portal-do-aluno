package org.osrapazes.portalaluno.models;

import java.time.LocalDateTime;

public record AssignmentRequest(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {}