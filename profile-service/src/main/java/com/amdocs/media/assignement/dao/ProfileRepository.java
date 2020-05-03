package com.amdocs.media.assignement.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.assignement.entity.Profile;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A repository for performing DB operations for User Profile
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{
	
	Optional<Profile> findProfileByUserName(String userName);
	
	void deleteByUserName(String userName);
}
