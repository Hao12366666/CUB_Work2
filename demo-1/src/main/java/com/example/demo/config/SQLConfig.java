package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@PropertySource("classpath:config.properties")
public class SQLConfig {
	@Value("${mongodb.uri}")
	private String mongoUri;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongoUri);
	}

}
