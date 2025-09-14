package br.com.felipefmb.competitors.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Movies API",
                version = "v1",
                description = "Catálogo de filmes",
                contact = @Contact(name = "Equipe", email = "dev@example.com")
        )
)
public class OpenApiConfig {
}
