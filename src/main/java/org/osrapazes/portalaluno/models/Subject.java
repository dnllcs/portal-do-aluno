package org.osrapazes.portalaluno.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@EqualsAndHashCode(exclude = { "subjects"})
@ToString(exclude = { "subjects"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "students"})
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

	public Subject(String professor, String name) {
		this.professor = professor;
		this.name = name;
	}

	public Set<Student> getStudents() {
		return this.students;
	}
}






