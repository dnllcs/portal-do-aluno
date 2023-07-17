package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "aluno")
@Data
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_aluno")
	private Long id;
	@Column(name = "nome")
	private String name;
	private String cpf;
	private String rg;
	private String email;
	private String senha;
	private boolean status;
}