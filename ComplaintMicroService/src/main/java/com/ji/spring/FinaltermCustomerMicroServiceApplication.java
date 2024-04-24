package com.ji.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class FinaltermCustomerMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinaltermCustomerMicroServiceApplication.class, args);
		System.out.println("Complaint Manager Microservice started");
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
