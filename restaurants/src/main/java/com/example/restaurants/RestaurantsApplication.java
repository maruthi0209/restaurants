package com.example.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *  This is the main function of the application.
 *  @Enable Eureka client is for eureka service implementation
 *  @ComponentScan to scan for whole application.
 * @author schennapragada
 *
 */
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages ="com.example.restaurants.*")
public class RestaurantsApplication {

	/**
	 *  Main method for the application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestaurantsApplication.class, args);
	}

}
