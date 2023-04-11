package com.nsk.spring.Microservice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.nsk.spring.Microservice"})
@EnableAutoConfiguration
public class APIStarter extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(APIStarter.class, args);
	}
	
	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(APIStarter.class);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}
}
