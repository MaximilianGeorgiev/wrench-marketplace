package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.sampleproject.wrenchmarketplace.dao.RoleRepository;
import com.sampleproject.wrenchmarketplace.dao.UserRepository;
import com.sampleproject.wrenchmarketplace.entities.User;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	public UserServiceImpl (UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	private String hashPassword(String password){
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(int theId) {
		return userRepository.findById(theId);
	}
	
	@Override
	public Optional<User> findByusername(String username){
		return userRepository.findByusername(username);
	}
	
	@Override
	public Optional<User> findByemail(String email){
		return userRepository.findByemail(email);
	}

	@Override
	public void save(User seller) {
		seller.setPassword(hashPassword(seller.getPassword()));
		userRepository.save(seller);
	}

	@Override
	public void deleteById(int theId) {
		userRepository.deleteById(theId);
	}
}
