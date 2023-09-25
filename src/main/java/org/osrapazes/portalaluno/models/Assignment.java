package org.osrapazes.portalaluno.models;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "atividade")
@EqualsAndHashCode(exclude = { "subjects"})
@ToString(exclude = { "subjects"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atividade_id")
	private Long assignmentId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "disciplina_id")
	@JsonIgnoreProperties("assignments")
	private Subject subject;
	private String title;
	private String description;


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endDate;


	public void addSubject(Subject subject) {
		this.subject = subject;
	}

}