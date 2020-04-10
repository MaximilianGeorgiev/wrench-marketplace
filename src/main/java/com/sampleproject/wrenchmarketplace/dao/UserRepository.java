package com.sampleproject.wrenchmarketplace.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import com.sampleproject.wrenchmarketplace.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/*
	 * naming convention must be followed so no camelcase ;\\ 
	 * JPA automatically calls built in functions to find by fields existing in the database
	 */
	Optional<User> findByusername(String username);
	
	Optional<User> findByemail(String email);
}
