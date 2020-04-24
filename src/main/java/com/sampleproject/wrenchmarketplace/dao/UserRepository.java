package com.sampleproject.wrenchmarketplace.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sampleproject.wrenchmarketplace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/*
	 * naming convention must be followed so no camelcase ;\\ 
	 * JPA automatically calls built in functions to find by fields existing in the database
	 */
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO USER_LISTING (user_Id, listing_Id) VALUES (:user_Id, :listing_Id);", nativeQuery = true)
	public void insertIntoJoinedTable(@Param("user_Id") Integer user_Id, @Param("listing_Id") Integer listing_Id);
	
	/*
	 * Returns one (true) if user_id == listing_id (should be one match only anyway) and returns 0 (false) if none is found
	 */
	@Query(value = "SELECT CAST(COUNT(*) AS BIT) FROM USER_LISTING u WHERE u.user_Id = :user_Id AND u.listing_Id = :listing_Id", nativeQuery = true)
	public boolean findByListingIdInJoinedTable(@Param("user_Id") Integer user_Id, @Param("listing_Id") Integer listing_Id);
	
	Optional<User> findByusername(String username);
	
	Optional<User> findByemail(String email);
}
