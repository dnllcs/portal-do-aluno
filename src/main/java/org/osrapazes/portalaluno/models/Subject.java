package org.osrapazes.portalaluno.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "disciplina")
@EqualsAndHashCode(exclude = { "subjects", "assignments"})
@ToString(exclude = { "subjects", "assignments"})
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "subjectId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "assignments"})
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "disciplina_id")
	private Long subjectId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "professor_id")
	@JsonIgnoreProperties("subjects")
	private Professor professor;

	@Column(name= "nome")
	private String name;

	@ManyToMany(mappedBy = "subjects")
	@JsonIgnoreProperties("subjects")
	private Set<Student> students = new HashSet<>();

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("subject")
	private Set<Assignment> assignments;

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Subject(Professor professor, String name) {
		this.professor = professor;
		this.name = name;
	}
  
	public Set<Student> getStudents() {
		return this.students;
	}

	public void addAssignment(Assignment assignment) {
		this.assignments.add(assignment);
		assignment.addSubject(this);
	}
	
	public void removeAssignment(Assignment assignment) {
		assignments.remove(assignment);
	}
}