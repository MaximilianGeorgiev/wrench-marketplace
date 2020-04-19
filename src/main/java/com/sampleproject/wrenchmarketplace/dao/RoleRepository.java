package com.sampleproject.wrenchmarketplace.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sampleproject.wrenchmarketplace.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	public Role findByname(String theRoleName);
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO AUTHORITIES (username, authority) VALUES (:user, :auth)", nativeQuery = true)
	 void insertIntoAuthorities(@Param("user") String user, @Param("auth") String auth);
	
	/*
	 * Reason this query was implemented was that I found no other way to make a connection between USERS, ROLE, USER_ROLE AND
	 * AUTHORITIES. AUTHORITIES AND USERS are both prebuilt tables used by spring security and are required to function and calls 
	 * to /authenticateUser for example require the username of the user to be in AUTHORITIES. That's why I did it manually
	 */
}
