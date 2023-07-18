package org.osrapazes.portalaluno.repositories;

import java.util.Optional;

import org.osrapazes.portalaluno.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByEmail(String email);
}