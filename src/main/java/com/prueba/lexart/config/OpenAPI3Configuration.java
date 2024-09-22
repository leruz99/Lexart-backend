package com.prueba.lexart.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenAPI3Configuration {
    @Value("${swagger.api-url}")
    String apiUrl;

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(
                                "JWT",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Prueba Tecnica")
                        .description("Prueba-tecnica")
                        .version("v1.0.0")
                        .contact(new Contact().name("Luis Ruz").email("lruzmenco@gmail.com")))
                .addSecurityItem(new SecurityRequirement().addList("JWT", Arrays.asList("read", "write")))
                .servers(List.of(new Server().url(apiUrl)));
    }
}
