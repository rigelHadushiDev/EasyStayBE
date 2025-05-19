package com.example.EasyStay.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EasyStay – Hotel Booking & Management System")
                        .description("EasyStay is a platform where hotel managers can register their hotels and rooms they want to add and users can book different hotels or rooms based on their needs.\n")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rigel Hadushi")
                                .url("https://github.com/rigelHadushiDev")
                                .email("rigelhadushi4@gmail.com"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Source code & issues")
                        .url("https://github.com/rigelHadushiDev/EasyStayBE"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
