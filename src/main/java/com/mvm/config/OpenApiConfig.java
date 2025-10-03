package com.mvm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI schoolManagementOpenAPI() {
		return new OpenAPI().info(new Info().title("Maharishi Vidya Mandir School API")
				.description("API documentation for School Management System built with Spring Boot").version("1.0.0")
				.contact(new Contact().name("Ashish Singh").email("ashishsinghvtf@example.com")
						.url("https://github.com/singh-ashish08"))
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
