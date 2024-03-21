package com.keiko.shopservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (
        info = @Info (
                title = "Shops Api",
                description = "Shop service", version = "1.0.0",
                contact = @Contact (
                        name = "Keiko Mikhail",
                        email = "mihaila4038@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
