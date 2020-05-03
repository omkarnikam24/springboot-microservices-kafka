package com.amdocs.media.assignement.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.dao.ProfileRepository;
import com.amdocs.media.assignement.entity.Profile;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote Implementation of a Profile Service
 */
@Service
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	@Autowired
	private ProfileRepository profileRepo;

	@Override
	public Profile saveProfile(Profile profile) {
		return profileRepo.save(profile);
	}

	@Override
	public void updateProfile(Profile profile) {
		Optional<Profile> existingProfile = profileRepo.findProfileByUserName(profile.getUserName());
		if (existingProfile.isPresent()) {
			Profile newProfile = existingProfile.get();
			newProfile.setAddress(profile.getAddress());
			newProfile.setPhoneNumber(profile.getPhoneNumber());
			profileRepo.save(newProfile);
			log.info("Successfully updated profile for user : {}", profile.getUserName());
		} else {
			log.info("Could not update profile for user : {} as it doesn't exist", profile.getUserName());
		}
	}

	@Override
	@Transactional
	public void deleteProfile(Profile profile) {
		if (null != profile && null != profile.getUserName()) {
			profileRepo.deleteByUserName(profile.getUserName());
			log.info("Successfully deleted profile for user : {}", profile.getUserName());
		}
	}
}
