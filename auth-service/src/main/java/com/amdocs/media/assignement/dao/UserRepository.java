package com.amdocs.media.assignement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.assignement.entity.AppUser;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote User Repository to perform DB operations
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

	AppUser findAppUserByUsername(String username);
}
