package com.ji.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class FinaltermSpringCloudEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinaltermSpringCloudEurekaServerApplication.class, args);
		System.out.println("Complaint Manager Eureka Server started");
	}

}
