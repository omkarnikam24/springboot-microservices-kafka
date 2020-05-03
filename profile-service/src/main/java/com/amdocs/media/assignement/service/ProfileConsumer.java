package com.amdocs.media.assignement.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.dto.ProfileDTO;
import com.amdocs.media.assignement.util.ProcessType;
import com.amdocs.media.assignement.util.Utility;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A Subscriber which consumes messages for Profile
 */
@Service
public class ProfileConsumer {

	private static final String GROUP_ID = "profile";

	private static final String TOPIC_NAME = "profile-topic";
	
	@Autowired
	private ProfileService profileService;
	
	private static final Logger log = LoggerFactory.getLogger(ProfileConsumer.class);
	
	/**
	 * @apiNote Consumes messages from the topic
	 * @param profileDto
	 * @throws IOException
	 */
	@KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
	public void consume(ProfileDTO profileDto) {
		log.info("Event for user : {}", profileDto.getUserName());
		if(ProcessType.UPDATE.toString().equalsIgnoreCase(profileDto.getType())) {
			log.info("Consuming {} event", ProcessType.UPDATE);
			profileService.updateProfile(Utility.convertToEntity(profileDto));
		} else if (ProcessType.DELETE.toString().equalsIgnoreCase(profileDto.getType())) {
			log.info("Consuming {} event", ProcessType.DELETE);
			profileService.deleteProfile(Utility.convertToEntity(profileDto));
		} else
			log.info("Invalid Operation");
	}
}
