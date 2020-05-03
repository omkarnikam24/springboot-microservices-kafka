package com.amdocs.media.assignement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.amdocs.media.assignement.entity.Profile;
import com.amdocs.media.assignement.service.ProfileService;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A Rest Controller for processing User Profile Requests
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private ProfileService service;

	/**
	 * @param profile
	 * @return ResponseEntity
	 * @exception ResponseStatusException
	 */
	@PostMapping
	public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
		log.info("Creating a Profile for : {}", profile.getUserName());
		try {
			return new ResponseEntity<Profile>(service.saveProfile(profile), HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile already exists");
		}
	}
}
