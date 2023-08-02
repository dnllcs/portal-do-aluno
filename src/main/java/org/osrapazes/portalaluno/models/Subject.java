package org.osrapazes.portalaluno.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "assignments"})
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "disciplina_id")
	private Long subjectId;
	private String professor;

	@Column(name= "nome")
	private String name;

	@ManyToMany(mappedBy = "subjects")
	@JsonIgnoreProperties("subjects")
	private Set<Student> students = new HashSet<>();

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("subject")
	private Set<Assignment> assignments;

	public Subject(String professor, String name) {
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
		//assignment.removeSubject();
		assignments.remove(assignment);
	}
}