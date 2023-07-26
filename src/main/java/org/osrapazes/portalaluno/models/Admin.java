package org.osrapazes.portalaluno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Entity
@Table(name = "admin")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private Long adminId;

	@Column(name = "nome")
	private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
	private User user;
	
	private String email;

	private boolean status;

	public void addUser(User user) {
		this.user = user;
		user.setAdmin(this);
	}
	
}