package org.osrapazes.portalaluno.repositories;

import java.util.List;
import java.util.Optional;

import org.osrapazes.portalaluno.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = { "admin", "student" })
	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmailAll(@Param("email") String email);

	Optional<User> findByEmail(String email);

}