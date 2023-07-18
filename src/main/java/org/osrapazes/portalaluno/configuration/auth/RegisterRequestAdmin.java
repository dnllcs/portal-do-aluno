package org.osrapazes.portalaluno.configuration.auth;
import lombok.Builder;
import lombok.Data;
import lombok.Builder;

import org.osrapazes.portalaluno.models.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequestAdmin {

	private String name;
	private String email;
	private String password;
	private boolean status;

}