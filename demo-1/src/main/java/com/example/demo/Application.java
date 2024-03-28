package com.example.demo;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.service.RateUpdateService;
import com.mongodb.client.MongoClient;

@SpringBootApplication
@EnableScheduling

public class Application {

	@Autowired
	private RateUpdateService rateUpdateService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// 提供一批次每日 18:00 呼叫 API，取得外匯成交資料
	@Scheduled(cron = "0 0 18 * * ?")
	public void runScheduledTask() {
		rateUpdateService.mainService();
	}

	private final MongoClient mongoClient;

	public Application(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> {
			mongoClient.close();
			return 0;
		};
	}

	@PreDestroy
	public void closeMongoClient() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
}
