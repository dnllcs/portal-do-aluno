package org.osrapazes.portalaluno.repositories;

import java.util.Optional;

import org.osrapazes.portalaluno.models.Enrollment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	
	@Query("FROM Enrollment e WHERE e.enrollmentCode = :code")
	@EntityGraph(attributePaths = {"student"})
	Optional<Enrollment> findByEnrollmentCode(@Param("code") String code);


}