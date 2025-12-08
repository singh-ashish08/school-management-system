package com.mvm.config;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter; // make sure JwtAuthFilter is annotated @Component

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable());

        http.authorizeHttpRequests(request -> request
                // Swagger + static should be public
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
                .requestMatchers("/api/user/register", "/api/user/login").permitAll()
                .anyRequest().authenticated()
        );

        // stateless session
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // prevent browser basic auth popup (use JSON error)
        http.exceptionHandling(e -> e.authenticationEntryPoint(new RestAuthenticationEntryPoint()));

        // register your JwtAuthFilter instance (note: pass the autowired bean, not a method)
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
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


