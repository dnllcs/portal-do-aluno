package org.osrapazes.portalaluno.repositories;

import java.util.Optional;
import java.util.List;

import org.osrapazes.portalaluno.models.Assignment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	
	@Query("SELECT a FROM Assignment a, Subject su, Student st, IN (su.students) sust WHERE a.subject.subjectId = su.subjectId AND sust.studentId = st.studentId AND st.studentId = :id")
	List<Assignment> findAllAssingmentsByStudentId(@Param("id") Long id);

	@Query("SELECT a FROM Assignment a WHERE a.subject.subjectId = :id")
	List<Assignment> findAllAssingmentsBySubjectId(@Param("id") Long id);

	@Query("FROM Assignment a WHERE a.title = :title")
	Optional<Assignment> findByTitle(String title);
}