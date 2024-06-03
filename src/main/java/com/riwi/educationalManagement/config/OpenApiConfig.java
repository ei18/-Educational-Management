package com.riwi.educationalManagement.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**Configuración de Swagger*/
@Configuration
@OpenAPIDefinition(info = @Info(title = "Api for online learning management", version = "1.0", description = "Management system to facilitate access to courses and educational materials. "))
public class OpenApiConfig {
}
