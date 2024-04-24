package com.ji.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class FinaltermMicroServiceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinaltermMicroServiceClientApplication.class, args);
		System.out.println("Complaint Manager client started");
	}

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new ComplaintHandlerConverter());
            }
        };
    }
	
}
