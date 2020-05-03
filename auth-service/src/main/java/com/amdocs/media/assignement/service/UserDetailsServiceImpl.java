package com.amdocs.media.assignement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.dao.UserRepository;
import com.amdocs.media.assignement.entity.AppUser;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote Implementation of a User Details Service
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = repo.findAppUserByUsername(username);
		if (null == appUser)
			throw new UsernameNotFoundException("Username: " + username + " not found!");

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
		return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
	}

}
