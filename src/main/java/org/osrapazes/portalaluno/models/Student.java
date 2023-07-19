package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "aluno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aluno_id")
	private Long studentId;
	@Column(name = "nome")
	private String name;

	private String cpf;

	private String rg;

	private String email;

	private String password;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	private boolean status;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	@Override
	public String getPassword() {
		return this.password;	
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
		return this.status;
	}
}