package com.mvm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//to change the default security configuration ,we have to return object of SecurityFilterChain 
	
	@Bean
	public  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 	
		http.csrf(customizer->customizer.disable());//disable default csrf
		http.authorizeHttpRequests(request->request.anyRequest().authenticated());//enable authentication for any request
		//http.formLogin(Customizer.withDefaults());//to get default login form-we dont need formlogin for stateless
		http.httpBasic(Customizer.withDefaults());//to get the basic auth on postman
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//to create a new session for every request/stateless 
		
		return http.build();
		}

}
