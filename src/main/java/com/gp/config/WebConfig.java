package com.gp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gp.MasterGeneralConstant;

@Configuration
public class WebConfig {

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(MasterGeneralConstant.baseRestURL+"/*").allowedOrigins("http://localhost:4200").allowedMethods("PUT","GET","POST");
            }
        };
    }
}