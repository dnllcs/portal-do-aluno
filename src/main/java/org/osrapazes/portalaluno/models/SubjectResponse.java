package org.osrapazes.portalaluno.models;

import java.util.List;

import org.osrapazes.portalaluno.models.Assignment;

public record SubjectResponse(String nome, String professor, List<Assignment> assignments) {}