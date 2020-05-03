package com.amdocs.media.assignement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.dto.ProfileDTO;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A Producer Service
 */
@Service
public class AuthProducer {
	
	private static final Logger log = LoggerFactory.getLogger(AuthProducer.class);
	
	@Value("${com.amdocs.kafka.topic-name}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, ProfileDTO> kafkaTemplate;
	
	/**
	 * @apiNote Publishes a message to the Kafka Cluster
	 * @param profile
	 */
	public void sendMessage(ProfileDTO profile) {
		log.info("Publishing {} event for user : {}", profile.getType(), profile.getUserName());
		kafkaTemplate.send(topicName, profile);
	}
}
