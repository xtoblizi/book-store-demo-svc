package com.book.store.svc.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("ABC Book Store Service API Doc")
                .version("1.0.0")
                .description("HTTP APIs to manage book store management, shopping and check-out operations")
                .contact(new Contact().name("support@abcbookstore.com"));
    }

}
