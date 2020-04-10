package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import com.sampleproject.wrenchmarketplace.entities.User;

public interface UserService {

	public List<User> findAll();
	
	public Optional<User> findById(int theId);
	
	public Optional<User> findByusername(String userName);
	
	public Optional<User> findByemail(String email);
		
	public void save(User seller);
	
	public void deleteById(int theId);

}
