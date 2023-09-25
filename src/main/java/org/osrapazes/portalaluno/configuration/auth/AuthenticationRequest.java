package org.osrapazes.portalaluno.configuration.auth;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
//Formato da resposta enviada pra o controller AuthenticationController
@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest {
	
	private String email;
	private String password;
}