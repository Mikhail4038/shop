package com.keiko.userservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (
        info = @Info (
                title = "Users Api",
                description = "User service", version = "1.0.0",
                contact = @Contact (
                        name = "Keiko Mikhail",
                        email = "mihaila4038@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
