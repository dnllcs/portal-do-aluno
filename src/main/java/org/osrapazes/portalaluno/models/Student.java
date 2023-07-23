package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Entity
@Table(name = "aluno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(exclude = { "subjects"})
@ToString(exclude = { "subjects"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

	private LocalDate birthDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "aluno_disciplina",
		joinColumns = @JoinColumn(name = "aluno_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
	@JsonIgnoreProperties("students")
	private Set<Subject> subjects = new HashSet<>();

	public void addSubject(Subject subject) {
		this.subjects.add(subject);
		subject.getStudents().add(this);
	}

	public void removeSubject(Subject subject) {
		this.subjects.remove(subject);
	}

	public List<Subject> getSubjects() {
		return List.copyOf(this.subjects);
	}

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