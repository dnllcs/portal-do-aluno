package org.osrapazes.portalaluno.init;

import org.osrapazes.portalaluno.configuration.auth.AuthenticationService;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestAdmin;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestStudent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Cria na inicializacao da aplicacao um registro de aluno(email:alunoTeste@gmail.com senha:123) 
//e um registro de admin(email:adminTeste@gmail.com senha:123)
@Component
public class ApplicationStartRunner implements CommandLineRunner {


	private AuthenticationService authenticationService;


	public ApplicationStartRunner(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void run(String ...args) throws Exception {
		authenticationService.register(new RegisterRequestStudent("alunoTeste", "alunoTeste@gmail.com", "123", "cpf123", "rg123", true));
		authenticationService.register(new RegisterRequestAdmin("adminTeste", "adminTeste@gmail.com", "123", true));

	}
	
}