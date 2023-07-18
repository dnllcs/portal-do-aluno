package org.osrapazes.portalaluno.configuration.auth;
import lombok.Builder;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest {
	private String email;
	private String password;
}