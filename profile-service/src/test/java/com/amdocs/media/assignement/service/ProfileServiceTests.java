package com.amdocs.media.assignement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amdocs.media.assignement.dao.ProfileRepository;
import com.amdocs.media.assignement.entity.Profile;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {

	@InjectMocks
	ProfileServiceImpl profileService;

	@Mock
	private ProfileRepository profileRepo;

	Profile profileExpected = new Profile();

	@BeforeEach
	void setMock() {
		profileExpected.setId(1);
		profileExpected.setUserName("Omkar");
		profileExpected.setAddress("Pune");
		profileExpected.setPhoneNumber(987654);
	}

	@Test
	void testUpdate() {
		Profile profile = new Profile();
		profile.setUserName("Omkar");
		profile.setAddress("Pune");
		profile.setId(1);
		profile.setPhoneNumber(987654);
		profileService.updateProfile(profile);
		verify(profileRepo, times(1)).findProfileByUserName("Omkar");
		assertEquals("Pune", profile.getAddress());
	}

	@Test
	void testDelete() {
		Profile profile = new Profile();
		profile.setUserName("Omkar");
		profile.setAddress("Pune");
		profile.setId(1);
		profile.setPhoneNumber(987654);
		profileService.deleteProfile(profile);
		verify(profileRepo, times(1)).deleteByUserName("Omkar");
	}

	@Test
	void testSave() {
		Profile profile = new Profile();
		profile.setUserName("Omkar");
		profile.setAddress("Pune");
		profile.setPhoneNumber(987654);
		profileService.deleteProfile(profile);
		doReturn(profileExpected).when(profileRepo).save(profile);
		when(profileService.saveProfile(profile)).thenReturn(profile);
		Profile expectedProfileOut = profileService.saveProfile(profile);
		assertEquals(profileExpected.getUserName(), expectedProfileOut.getUserName());
	}

}
