package com.appspring.appspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@SpringBootApplication
@EntityScan(basePackages = "com.appspring.appspring.modelo")
@ComponentScan(basePackages= {"com.*"})
@EnableJpaRepositories(basePackages = {"com.appspring.appspring.repository"})
@EnableTransactionManagement
public class AppSpringApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AppSpringApplication.class, args);
		
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppSpringApplication.class);
	}
	
}
