package com.example.komfortsoft;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//http://localhost:63908/swagger-ui/index.html
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MaxNumber API")
                        .description("API для поиска N-го максимального числа в Excel файле")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }


}