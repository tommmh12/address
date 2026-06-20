package com.myproject.backend.addressmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI addressManagementOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Address Management API")
                        .description("API quan ly chi nhanh, dia chi va tai san")
                        .version("v1")
                        .contact(new Contact().name("Address Management Team"))
                        .license(new License().name("Internal Use")));
    }
}
