package org.osrapazes.portalaluno.repositories;

import java.util.List;
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

	@EntityGraph(attributePaths = {"students", "professor"})
	@Query("SELECT s FROM Subject s, Professor p, IN (p.subjects) ps WHERE s.subjectId = ps.subjectId AND s.name = :nameSubject AND p.name = :nameProfessor")
	Optional<Subject> findByNameAndProfessorEagerly(@Param("nameProfessor") String nameProfessor, @Param("nameSubject") String nameSubject);
	
	@EntityGraph(attributePaths = {"students", "assignments"})
	@Query("FROM Subject s WHERE s.subjectId = :id")
	Optional<Subject> findByIdEagerly(@Param("id") Long id);

	@Query("SELECT s FROM Subject s, IN (s.students) st WHERE st.studentId = :id")
	List<Subject> findByStudentId(@Param("id") Long id);
} 