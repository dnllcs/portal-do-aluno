package org.osrapazes.portalaluno.repositories;

import java.util.Optional;

import org.osrapazes.portalaluno.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	Optional<Admin> findByEmail(String email);
}