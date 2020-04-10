package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sampleproject.wrenchmarketplace.dao.UserRepository;
import com.sampleproject.wrenchmarketplace.entities.User;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	
	public UserServiceImpl (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/*
	private String hashPassword(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	*/
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(int theId) {
		return userRepository.findById(theId);
	}
	

	@Override
	public void save(User seller) {
		//seller.setPassword(hashPassword(seller.getPassword()));
		userRepository.save(seller);
	}

	@Override
	public void deleteById(int theId) {
		userRepository.deleteById(theId);
	}

}
