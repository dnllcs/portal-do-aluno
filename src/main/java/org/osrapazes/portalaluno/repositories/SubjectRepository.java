package org.osrapazes.portalaluno.repositories;

import java.util.Optional;

import org.osrapazes.portalaluno.models.Subject;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

	Optional<Subject> findByName(String name);
	Optional<Subject> findByProfessor(String professor);
	
	@EntityGraph(attributePaths = {"students"})
	Optional<Subject> findByNameAndProfessor(String name, String professor);

	@EntityGraph(attributePaths = {"assignments", "assignments.subject"})
	@Query("FROM Subject s WHERE s.subjectId = :id")
	Optional<Subject> findByIdEagerly(@Param("id") Long id);


}