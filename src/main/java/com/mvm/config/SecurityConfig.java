package com.mvm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	private UserDetailsService  userDetailsService;//we have to provide implementation for this interface on service layer
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(12);
	    }
	
	@Bean
	public AuthenticationProvider authProvider() { // used to provide authentication to user
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();//to connect with database
		provider.setUserDetailsService(userDetailsService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//NoOpPasswordEncoder is used when we don't need encoded password
		provider.setPasswordEncoder(passwordEncoder()); // ✅ use Spring bean line no. 28
        return provider;
		
	}
	
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
	
		/*
		 * //changing default setting of UserDetailsService //hardcoding the user values
		 * like username,password and roles
		 * 
		 * @Bean public UserDetailsService userDetailsService() { UserDetails user =
		 * User //class from spring security .withDefaultPasswordEncoder()
		 * .username("ashish") .password("ashish@123") .roles("ADMIN") .build();
		 * 
		 * UserDetails user1 = User //class from spring security
		 * .withDefaultPasswordEncoder() .username("deepa") .password("deepa@123")
		 * .roles("USER") .build();
		 * 
		 * return new InMemoryUserDetailsManager(user,user1); //returning the object of
		 * UserDetails }
		 */

}
