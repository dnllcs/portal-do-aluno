package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.time.LocalDate;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "professor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(exclude = {"subjects", "user"})
@ToString(exclude = { "subjects", "user"})
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "professorId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "subjects", "user"})
public class Professor{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "professor_id")
	private Long professorId;

	@Column(name = "nome")
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@JsonIgnore
	private User user;
	private String cpf;
	private String rg;
	private String email;
	private boolean status;
	private LocalDate birthDate;

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("professor")
	private Set<Subject> subjects = new HashSet<>();

	public void addUser(User user) {
		this.user = user;
		user.setProfessor(this);
	}

	public void addSubject(Subject subject) {
		this.subjects.add(subject);
		subject.setProfessor(this);
	}
  
	public void removeSubject(Subject subject) {
		this.subjects.remove(subject);
	}

	public List<Subject> getSubjectsAsList() {
		return List.copyOf(this.subjects);
	}

	public Set<Subject> getSubjects() {
		return this.subjects;
	}

	public void setSubject(Set<Subject> subjects) {
		this.subjects = subjects;
	}

}

