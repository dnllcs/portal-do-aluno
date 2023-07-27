package org.osrapazes.portalaluno.configuration.auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
//Formato da resposta enviada pra o controller AuthenticationController
@Data
@Builder
@AllArgsConstructor

public class RegisterRequestStudent {
	

	private String name;
	private String email;
	private String password;
	private String cpf;
	private String rg;
	private LocalDate birthDate;
}