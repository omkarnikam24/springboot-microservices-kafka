package com.amdocs.media.assignement;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@EnableEurekaClient
public class AuthServiceApplication {
	
	@Value("${com.amdocs.kafka.topic-name}")
	private String topicName;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	
	/**
	 * Creates Kafka Topic
	 * @return
	 */
	@Bean
	public NewTopic createTopic() {
		return TopicBuilder.name(topicName).build();
	}
	
}
