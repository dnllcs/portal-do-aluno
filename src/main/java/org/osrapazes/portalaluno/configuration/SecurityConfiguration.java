package org.osrapazes.portalaluno.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug=true)
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.
					csrf(csrf -> csrf.disable())
					.authorizeHttpRequests()
					.requestMatchers("api/v1/auth/**")
					.permitAll()
					.anyRequest()
					.authenticated()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authenticationProvider(authenticationProvider)
					.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
					.build();


	}
}