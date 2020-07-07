package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;

import com.sampleproject.wrenchmarketplace.entities.User;

public interface UserService {
	
	public boolean isUserLoggedIn(Authentication loggedInUser);
	
	public void editPassword(Integer Id, String password);
	
	public void editFirstName(Integer Id, String firstName);
	
	public void editSecondName(Integer Id, String secondName);
	
	public void editEmail(Integer Id, String email);
	
	public void editAge(Integer Id, Integer age);
	
	public void insertIntoUserListingJoinedTable(Integer user_Id, Integer listing_Id);
	
	public void insertIntoWatchListJoinedTable(Integer user_Id, Integer listing_Id);
	
	public boolean findByListingIdInJoinedTable(Integer user_Id, Integer listing_Id);
	
	public boolean isUserWatchingListingId(Integer user_Id, Integer listing_Id);
	
	public void deleteFromWatchListJoinedTable(Integer user_Id);

	public List<User> findAll();
	
	public Optional<User> findById(int theId);
	
	public Optional<User> findByusername(String userName);
	
	public Optional<User> findByemail(String email);
		
	public void save(User seller);
	
	public void deleteById(int theId);

}
