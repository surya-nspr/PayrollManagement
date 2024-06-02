package com.hexaware.payrollmanagementsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hexaware.payrollmanagementsystem")
public class PayrollmanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayrollmanagementsystemApplication.class, args);
	}
	 @Bean
	    public ModelMapper getModelMapper() { 
	        return new ModelMapper(); 
	    } 
}
