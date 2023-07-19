package org.osrapazes.portalaluno.configuration.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
//Formato da resposta enviada pra o controller AuthenticationController
@Data
@Builder
@AllArgsConstructor
public class RegisterRequestAdmin {

	private String name;
	private String email;
	private String password;
	private boolean status;

}