package com.amdocs.media.assignement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.amdocs.media.assignement.dto.ProfileDTO;

@ExtendWith(MockitoExtension.class)
public class AuthProducerTests {
	
	@Mock
	private KafkaTemplate<String, ProfileDTO> kafkaTemplate;
	
	@InjectMocks
	private AuthProducer producerService;
	
	@BeforeEach
	void setMock() {
		ReflectionTestUtils.setField(producerService, "topicName", "profile-topic");
	}
	
	@Test
    void testUpdate() {
		String topic = "profile-topic";
		ProfileDTO profile = new ProfileDTO();
		profile.setUserName("omkar");
		profile.setAddress("Pune");
		profile.setPhoneNumber(888888888);
		profile.setType("UPDATE");
        producerService.sendMessage(profile);
        verify(kafkaTemplate, times(1)).send(topic, profile);
        assertEquals("profile-topic", topic);
        assertEquals("UPDATE", profile.getType());
    }
	
	@Test
    void testUpdateTopicNull() {
		ReflectionTestUtils.setField(producerService, "topicName", null);
		String topic = null;
		ProfileDTO profile = new ProfileDTO();
		profile.setUserName("omkar");
		profile.setAddress("Pune");
		profile.setPhoneNumber(888888888);
		profile.setType("UPDATE");
        producerService.sendMessage(profile);
        verify(kafkaTemplate, times(1)).send(topic, profile);
        assertEquals(null, topic);
        assertEquals("UPDATE", profile.getType());
    }
	
}
