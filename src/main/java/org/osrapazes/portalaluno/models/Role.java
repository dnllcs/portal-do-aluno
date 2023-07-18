package org.osrapazes.portalaluno.models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "cargo")
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cargo_id")
	private Long roleId;
	private String authority;
	public Role() {
		super();
	}
	public Role(String authority) {
		this.authority = authority;
	}
	public Role(Long roleId, String authority) {
		this.roleId = roleId;
		this.authority = authority;
	}
	public Long getroleId() {
		return roleId;
	}
	public void setroleId(Long roleId) {
		this.roleId = roleId;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String getAuthority() {
		return this.authority;
	}
	
}