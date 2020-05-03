package com.amdocs.media.assignement.service;

import com.amdocs.media.assignement.entity.Profile;

public interface ProfileService {

	Profile saveProfile(Profile profile);
	
	void updateProfile(Profile profile);
	
	void deleteProfile(Profile profile);
}
