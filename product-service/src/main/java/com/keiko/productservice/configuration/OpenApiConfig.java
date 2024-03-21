package com.keiko.productservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (
        info = @Info (
                title = "Products Api",
                description = "Product service", version = "1.0.0",
                contact = @Contact (
                        name = "Keiko Mikhail",
                        email = "mihaila4038@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
