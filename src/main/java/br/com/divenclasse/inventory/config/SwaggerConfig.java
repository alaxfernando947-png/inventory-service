package br.com.divenclasse.inventory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Divenclasse — Inventory Service API")
                        .description("Microserviço de controle de estoque para roupas sociais.")
                        .version("1.0.0")
                        .contact(new Contact().name("Equipe Divenclasse").email("dev@divenclasse.com.br")));
    }
}
