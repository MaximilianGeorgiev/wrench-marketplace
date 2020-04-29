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
	@Query(value = "UPDATE USERS u SET u.password = :password WHERE u.Id = :Id", nativeQuery = true)
	public void editPassword(@Param("Id")Integer Id, @Param("password") String password);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERS u SET u.firstName = :firstName WHERE u.Id = :Id", nativeQuery = true)
	public void editFirstName(@Param("Id")Integer Id, @Param("firstName") String firstName);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERS u SET u.secondName = :secondName WHERE u.Id = :Id", nativeQuery = true)
	public void editSecondName(@Param("Id")Integer Id, @Param("secondName") String secondName);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERS u SET u.email = :email WHERE u.Id = :Id", nativeQuery = true)
	public void editEmail(@Param("Id")Integer Id, @Param("email") String email);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERS u SET u.age = :age WHERE u.Id = :Id", nativeQuery = true)
	public void editAge(@Param("Id")Integer Id, @Param("age") Integer age);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO USER_LISTING (user_Id, listing_Id) VALUES (:user_Id, :listing_Id);", nativeQuery = true)
	public void insertIntoUserListingJoinedTable(@Param("user_Id") Integer user_Id, @Param("listing_Id") Integer listing_Id);
	
	/* 
	 * Didn't find a better way to make a watch list besides a joined table
	 */
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO USER_WATCHEDLISTING (user_Id, listing_Id) VALUES (:user_Id, :listing_Id);", nativeQuery = true)
	public void insertIntoWatchListJoinedTable(@Param("user_Id") Integer user_Id, @Param("listing_Id") Integer listing_Id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM USER_WATCHEDLISTING u WHERE u.user_Id = :user_Id", nativeQuery = true)
	public void deleteFromWatchListJoinedTable(@Param("user_Id") Integer user_Id);
	
	/*
	 * Returns one (true) if user_id == listing_id (should be one match only anyway) and returns 0 (false) if none is found
	 */
	@Query(value = "SELECT CAST(COUNT(*) AS BIT) FROM USER_LISTING u WHERE u.user_Id = :user_Id AND u.listing_Id = :listing_Id", nativeQuery = true)
	public boolean findByListingIdInJoinedTable(@Param("user_Id") Integer user_Id, @Param("listing_Id") Integer listing_Id);
	
	@Query(value = "SELECT CAST(COUNT(*) AS BIT) FROM USER_WATCHEDLISTING u WHERE u.user_Id = :user_Id AND u.listing_Id = :listing_Id", nativeQuery = true)
	public boolean isUserWatchingListingId(@Param("user_Id") Integer user_Id, @Param("listing_Id") Integer listing_Id);
	
	Optional<User> findByusername(String username);
	
	Optional<User> findByemail(String email);
}
