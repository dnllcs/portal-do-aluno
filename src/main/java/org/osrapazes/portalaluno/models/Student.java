package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "aluno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(of = "studentId", exclude = { "subjects", "user", "enrollment"})
@ToString(exclude = { "subjects", "user", "enrollment"})
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "studentId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "subjects", "enrollment", "user"})
public class Student{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aluno_id")
	private Long studentId;

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
	
	@OneToOne
	@JoinColumn(name = "matricula_id")
	@JsonIgnoreProperties("student")
	private Enrollment enrollment;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "aluno_disciplina",
		joinColumns = @JoinColumn(name = "aluno_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
	@JsonIgnoreProperties("students")
	private Set<Subject> subjects = new HashSet<>();

	public void addEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
		enrollment.addStudent(this);
	} 

	public void addUser(User user) {
		this.user = user;
		user.setStudent(this);
	}

	public void addSubject(Subject subject) {
		this.subjects.add(subject);
		subject.getStudents().add(this);
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

	public Student(StudentRequestDTO data){
		this.name = data.name();
		this.cpf = data.cpf();
		this.rg = data.rg();
		this.email = data.email();
	}
}

