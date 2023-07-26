package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.EnumMap;
import java.util.HashMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.osrapazes.portalaluno.models.RoleEnum;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long userId;
	private String email;
	private String password;
	private RoleEnum role;
	@OneToOne(mappedBy = "user")
	private Student student;
	@OneToOne(mappedBy = "user")
	private Admin admin;

	//public EnumMap<RoleEnum, Optional> userProfile = new EnumMap<>(RoleEnum.class);

	public void setStudent(Student student) {
		this.student = student;
	}

	// public Optional<?> teste() {
	// 	//userProfile.add(RoleEnum.STUDENT, Optional.of(this.student));
	// 	List<Optional> list = Arrays.asList(Optional.ofNullable(this.student), Optional.ofNullable(this.admin));
	// 	//List<Optional> list = new ArrayList<>();
	// 	//list.add(Optional.ofNullable(this.student));
	// 	//list.add(Optional.ofNullable(this.admin));
	// 	// System.out.println(list.get(0).isPresent());
	// 	//System.out.println(list.stream().filter(Optional::isPresent).findFirst());
	// 	return list.stream().filter(Optional::isPresent).findFirst().get();
	// }

	public Optional<?> owner() {
		return Arrays.asList(Optional.ofNullable(this.student),
		 Optional.ofNullable(this.admin))
		.stream().filter(Optional::isPresent).findFirst().get();
	}

	public User() {}
}