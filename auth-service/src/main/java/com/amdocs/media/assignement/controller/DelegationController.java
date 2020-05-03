package com.amdocs.media.assignement.controller;

import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.amdocs.media.assignement.dto.ProfileDTO;
import com.amdocs.media.assignement.service.AuthProducer;
import com.amdocs.media.assignement.util.ProcessType;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A Delegation or a Proxy Controller which redirects the authenticated requests to profile service.
 */
@RestController
@RequestMapping("/assignement")
public class DelegationController {

	private static final Logger log = LoggerFactory.getLogger(DelegationController.class);

	@Autowired
	private EurekaClient client;
	
	@Autowired
	private AuthProducer producerService;
	
	@Value("${com.amdocs.external.application.name}")
	private String applicationName;

	private RestTemplate restTemplate;

	public DelegationController(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
	/**
	 * @param profile - user profile details
	 * @param request - an HTTP Servlet Request
	 * @param principal - contains details of an authenticated user
	 * @return ResponseEntity
	 * @exception ResponseStatusException
	 */
	@PostMapping("/profile")
	public ResponseEntity<Object> createProfile(@RequestBody ProfileDTO profile, HttpServletRequest request,
			Principal principal) {
		log.info("Creating a profile for user : {}", principal.getName());
		if (null == profile) {
			log.info("Empty request body");
			return new ResponseEntity<Object>("Empty request body", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		profile.setUserName(principal.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		HttpEntity<Object> httpEntity = new HttpEntity<>(profile, headers);
		
		// Fetches hostname and port number dynamically from Eureka Registry
		Application app = client.getApplication(applicationName);
		if(null == app) {
			log.info("Profile Service is not UP, please try again in 30 seconds");
			HashMap<String, String> responseMap = new HashMap<>();
			responseMap.put("message", "Profile Service is not UP, please try again in 30 seconds");
			return new ResponseEntity<Object>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		InstanceInfo instanceInfo = client.getApplication(applicationName).getInstances().stream()
				.filter(info -> info.getStatus().toString().equalsIgnoreCase("UP")).findFirst().get();
		log.info("Instance info fetched from Eureka, hostname : {}, port number: {}", instanceInfo.getHostName(), instanceInfo.getPort());
		String url = "http://" + instanceInfo.getHostName() + ":" + instanceInfo.getPort() + "/profile"; 
		try {
			return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * @param profile - user profile details
	 * @param principal - contains details of an authenticated user
	 * @return ResponseEntity
	 */
	@PutMapping("/profile")
	public ResponseEntity<String> updateProfile(@RequestBody ProfileDTO profile, Principal principal) {
		log.info("Publishing Update Profile Event");
		if (null == profile) {
			log.info("Empty request body");
			return new ResponseEntity<String>("Empty request body", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		profile.setType(ProcessType.UPDATE.toString());
		profile.setUserName(principal.getName());
		producerService.sendMessage(profile);
		return new ResponseEntity<String>(
				"Update Event has been successfully published for user : " + principal.getName(), HttpStatus.OK);
	}

	/**
	 * @param request - an HTTP Servlet Request
	 * @param principal - contains details of an authenticated user
	 * @return ResponseEntity
	 */
	@DeleteMapping("/profile")
	public ResponseEntity<String> deleteProfile(HttpServletRequest request, Principal principal) {
		log.info("Publishing Delete Profile Event");
		ProfileDTO profile = new ProfileDTO();
		profile.setType(ProcessType.DELETE.toString());
		profile.setUserName(principal.getName());
		producerService.sendMessage(profile);
		return new ResponseEntity<String>(
				"Delete Event has been successfully published for user : " + principal.getName(), HttpStatus.OK);
	}
}
