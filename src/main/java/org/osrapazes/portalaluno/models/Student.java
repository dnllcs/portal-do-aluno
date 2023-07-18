package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "aluno_id")
	private Long studentId;
	@Column(name = "nome")
	private String name;

	private String cpf;

	private String rg;

	private String email;

	private String password;

	// @ManyToMany(fetch=FetchType.EAGER)
	// @JoinTable(
	// 	name="student_role_junction",
	// 	joinColumns = {@JoinColumn(name="aluno_id")},
	// 	inverseJoinColumns = {@JoinColumn(name="cargo_id")}
	// )
	// private Set<Role> role;
	
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