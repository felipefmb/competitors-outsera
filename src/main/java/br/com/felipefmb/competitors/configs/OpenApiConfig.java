package br.com.felipefmb.competitors.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Competitors API",
                version = "v1",
                description = "Produções cinematográficas",
                contact = @Contact(name = "Felipe Marques Batista", email = "felipefmb@gmail.com")
        )
)
public class OpenApiConfig {
    @Bean
    GroupedOpenApi reservedList() {
        return GroupedOpenApi.builder()
                .group("reserved")
                .pathsToMatch(
                        "/v1/golden-raspberry-awards/producers/winners"
                )
                .pathsToExclude("/v1/producoes/admin/**")
                .build();
    }
}
