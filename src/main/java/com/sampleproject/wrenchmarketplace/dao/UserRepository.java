package com.sampleproject.wrenchmarketplace.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.sampleproject.wrenchmarketplace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/*
	 * naming convention must be followed so no camelcase ;\\ 
	 * JPA automatically calls built in functions to find by fields existing in the database
	 */
	
	//@Query("SELECT u FROM USERS u WHERE u.username = :username")
	Optional<User> findByusername(@Param("username") String username);
	
	Optional<User> findByemail(String email);
}
