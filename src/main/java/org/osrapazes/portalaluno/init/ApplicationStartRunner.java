package org.osrapazes.portalaluno.init;

import org.osrapazes.portalaluno.configuration.auth.AuthenticationService;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestAdmin;
import org.osrapazes.portalaluno.configuration.auth.RegisterRequestStudent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
public class ApplicationStartRunner implements CommandLineRunner {


	private AuthenticationService authenticationService;


	public ApplicationStartRunner(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	public void run(String ...args) throws Exception {
	}
	
}