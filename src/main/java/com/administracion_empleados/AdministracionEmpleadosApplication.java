package com.administracion_empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class AdministracionEmpleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdministracionEmpleadosApplication.class, args);
	}

	@Bean
    @Profile("dev")
    public PropertySourcesPlaceholderConfigurer devPropertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application.properties"));
        return configurer;
    }

    @Bean
    @Profile("prod")
    public PropertySourcesPlaceholderConfigurer prodPropertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application-prod.properties"));
        return configurer;
    }

}
