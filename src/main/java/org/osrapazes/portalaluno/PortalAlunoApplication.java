package org.osrapazes.portalaluno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PortalAlunoApplication {
	public static void main(String[] args) {
		SpringApplication.run(PortalAlunoApplication.class, args);
	}

}
