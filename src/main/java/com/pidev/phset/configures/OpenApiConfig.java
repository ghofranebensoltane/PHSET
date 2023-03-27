package com.pidev.phset.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;


@Configuration
public class OpenApiConfig {


    private static final String SCHEME_NAME = "Bearer-key";
    private static final String SCHEME = "Bearer";
    private static final String FORMAT = "JWT";
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                //.info("project")
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, createSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .addSecurityItem(new SecurityRequirement().addList(FORMAT));
    }
    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme(SCHEME)
                .bearerFormat(FORMAT);

    }
}