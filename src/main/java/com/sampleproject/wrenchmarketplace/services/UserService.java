package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sampleproject.wrenchmarketplace.entities.User;

public interface UserService {
	
	public void insertIntoJoinedTable(Integer user_Id, Integer listing_Id);
	
	public boolean findByListingIdInJoinedTable(Integer user_Id, Integer listing_Id);

	public List<User> findAll();
	
	public Optional<User> findById(int theId);
	
	public Optional<User> findByusername(String userName);
	
	public Optional<User> findByemail(String email);
		
	public void save(User seller);
	
	public void deleteById(int theId);

}
