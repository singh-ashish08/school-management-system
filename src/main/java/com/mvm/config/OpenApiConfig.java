package com.mvm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class OpenApiConfig { // for swagger UI
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI schoolManagementOpenAPI() {
        Info info = new Info()
                .title("Maharishi Vidya Mandir School API")
                .description("API documentation for School Management System built with Spring Boot")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Ashish Singh")
                        .email("ashishSingh@gmail.com")
                        .url("https://github.com/singh-ashish08"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"));

        // Add bearer security scheme + apply globally
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(info);
    }
}
