package com.gp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="com.gp.controller")
@EntityScan(basePackages="com.gp.entity")
@EnableJpaRepositories(basePackages="com.gp.repository")
@ConfigurationPropertiesScan(basePackages="com.gp.config")
public class Gp2CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gp2CoreApplication.class, args);
	}

}
