package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "matricula")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "matricula_id")
	private Long enrollmentId;
	private String enrollmentCode;

	@OneToOne(mappedBy = "enrollment")
	private Student student;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate enrollmentDate;

	public Enrollment() {}
	
	public void addStudent(Student student) {
		this.student = student;
	}

}