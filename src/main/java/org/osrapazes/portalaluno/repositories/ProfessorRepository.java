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

	@EntityGraph(attributePaths = {"subjects"})
	@Query("SELECT p FROM Professor p, IN (p.subjects) ps, IN (ps.students) pst WHERE p.professorId = ps.professor.professorId AND pst.studentId = :id")
	List<Professor> findByStudentId(@Param("id") Long id);
}

//@Query("SELECT a FROM Assignment a, Subject su, Student st, IN (su.students) sust WHERE a.subject.subjectId = su.subjectId AND sust.studentId = st.studentId AND st.studentId = :id")
//@Query("SELECT s FROM Subject s, Professor p, IN (p.subjects) ps WHERE s.subjectId = ps.subjectId AND s.name = :nameSubject AND p.name = :nameProfessor")