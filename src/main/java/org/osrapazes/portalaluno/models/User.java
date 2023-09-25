package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long userId;
	private String email;
	private String password;
	private RoleEnum role;
	@OneToOne(mappedBy = "user")
	private Student student;
	@OneToOne(mappedBy = "user")
	private Admin admin;
	@OneToOne(mappedBy = "user")
	private Professor professor;

	public User() {}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Optional<?> owner() {
		return Arrays.asList(Optional.ofNullable(this.student),
			Optional.ofNullable(this.admin),
			Optional.ofNullable(this.professor))
		.stream().filter(Optional::isPresent).findFirst().get();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}