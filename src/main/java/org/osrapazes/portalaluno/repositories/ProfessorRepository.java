package org.osrapazes.portalaluno.repositories;

import java.util.Optional;
import java.util.List;

import org.osrapazes.portalaluno.models.Professor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	@EntityGraph(attributePaths = {"subjects"})
	Optional<Professor> findByEmail(String email);
	Optional<Professor> findByName(String email);
}