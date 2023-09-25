package org.osrapazes.portalaluno.repositories;

import java.util.Optional;

import org.osrapazes.portalaluno.models.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByEmail(String email);
	@EntityGraph(attributePaths = {"subjects.assignments"})
	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Optional<Student> findByIdEagerly(@Param("id") Long id);

	Optional<Student> findById(Long id);
}