package com.example.FinanceDataBackend.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()

                // API Info
                .info(new Info()
                        .title("Finance Data API 💰")
                        .version("1.0")
                        .description("Financial Records Management System with Role-Based Access (Viewer, Analyst, Admin).\n\n" +
                                "🔑 Use Authorize button and pass JWT token.\n\n" +
                                "Example: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...")
                        .contact(new Contact()
                                .name("Finance Support")
                                .email("support@finance.com"))
                )

                // Server
                .servers(List.of(
                        new Server().url("http://localhost:8086").description("Local Server")
                ))

                // JWT Security
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}