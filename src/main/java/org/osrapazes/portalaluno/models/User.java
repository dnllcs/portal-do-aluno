package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.EnumMap;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
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
import lombok.NoArgsConstructor;
import org.osrapazes.portalaluno.models.RoleEnum;

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

	public User() {}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Optional<?> owner() {
		return Arrays.asList(Optional.ofNullable(this.student),
		 Optional.ofNullable(this.admin))
		.stream().filter(Optional::isPresent).findFirst().get();
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}