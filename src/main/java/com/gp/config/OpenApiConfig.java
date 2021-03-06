package com.gp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class OpenApiConfig {
	/*
	 * @Bean public OpenAPI customOpenAPI() { return new OpenAPI() .components(new
	 * Components()) .info(new Info().title("Contact Application API").description(
	 * "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."
	 * )); }
	 */
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
	
   
}