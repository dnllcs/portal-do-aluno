package org.osrapazes.portalaluno.configuration;

import org.osrapazes.portalaluno.models.Student;

import java.util.Optional;

import org.osrapazes.portalaluno.models.Admin;
import org.osrapazes.portalaluno.repositories.AdminRepository;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
//Responsavel por gerar Beans usados em outras partes do codigo
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final StudentRepository studentRepository;
	private final AdminRepository adminRepository;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Optional<Student> student = studentRepository.findByEmail(username);
				Optional<Admin> admin = adminRepository.findByEmail(username);
				return admin.isPresent() ? admin.get() : student.get();
				
			}
			
		};
	}
	
	@Bean
	public AuthenticationProvider AutheticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
