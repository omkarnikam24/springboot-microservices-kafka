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

import com.amdocs.media.assignement.dto.ProfileDTO;
import com.amdocs.media.assignement.entity.Profile;

@ExtendWith(MockitoExtension.class)
public class ProfileConsumerTests {

	@InjectMocks
	ProfileConsumer profileConsumer;

	@Mock
	private ProfileServiceImpl profileService;

	ProfileDTO profileDto;
	Profile profile;
	
	@BeforeEach
	void setMock() {
		profileDto = new ProfileDTO();
		profileDto.setUserName("Omkar");
		profileDto.setAddress("Pune");
		profileDto.setPhoneNumber(987654);
		profile = new Profile(profileDto.getUserName(), profileDto.getAddress(), profileDto.getPhoneNumber());
	}

	@Test
	void testConsumeUpdate() {
		profileDto.setType("UPDATE");
		profileConsumer.consume(profileDto);
		verify(profileService, times(1)).updateProfile(profile);
		assertEquals("Pune", profile.getAddress());
	}
	
	@Test
	void testConsumeDelete() {
		profileDto.setType("DELETE");
		profileConsumer.consume(profileDto);
		verify(profileService, times(1)).deleteProfile(profile);
		assertEquals("Pune", profile.getAddress());
	}

}
