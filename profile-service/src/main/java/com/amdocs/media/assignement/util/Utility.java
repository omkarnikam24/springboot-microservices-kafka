package com.amdocs.media.assignement.util;

import com.amdocs.media.assignement.dto.ProfileDTO;
import com.amdocs.media.assignement.entity.Profile;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A utility class to perform static operations
 */
public class Utility {

	/**
	 * @apiNote Converts a Profile DTO into a Profile Entity
	 * @param dto - A Data Transfer Object which is to be converted into an entity
	 * @return A converted Entity
	 */
	public static Profile convertToEntity(ProfileDTO dto) {
		return new Profile(dto.getUserName(), dto.getAddress(), dto.getPhoneNumber());
	}
}
