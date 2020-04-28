package com.sampleproject.wrenchmarketplace.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sampleproject.wrenchmarketplace.dao.RoleRepository;
import com.sampleproject.wrenchmarketplace.dao.UserRepository;
import com.sampleproject.wrenchmarketplace.entities.User;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public void deleteById(int theId) {
		userRepository.deleteById(theId);
	}

	@Override
	public void editPassword(Integer Id, String password) {
		password = this.hashPassword(password);
		userRepository.editPassword(Id, password);
	}

	@Override
	public void editFirstName(Integer Id, String firstName) {
		userRepository.editFirstName(Id, firstName);
	}

	@Override
	public void editSecondName(Integer Id, String secondName) {
		userRepository.editSecondName(Id, secondName);
	}

	@Override
	public void editEmail(Integer Id, String email) {
		userRepository.editEmail(Id, email);
	}

	@Override
	public void editAge(Integer Id, Integer age) {
		userRepository.editAge(Id, age);
	}

	@Override
	public void insertIntoUserListingJoinedTable(Integer user_Id, Integer listing_Id) {
		userRepository.insertIntoUserListingJoinedTable(user_Id, listing_Id);
	}
	
	@Override
	public void insertIntoWatchListJoinedTable(Integer user_Id, Integer listing_Id) {
		userRepository.insertIntoWatchListJoinedTable(user_Id, listing_Id);
	}

	@Override
	public boolean findByListingIdInJoinedTable(Integer user_Id, Integer listing_Id) {
		return userRepository.findByListingIdInJoinedTable(user_Id, listing_Id);
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
	public Optional<User> findByusername(String username) {
		return userRepository.findByusername(username);
	}

	@Override
	public Optional<User> findByemail(String email) {
		return userRepository.findByemail(email);
	}

	@Override
	public void save(User seller) {
		seller.setPassword("{bcrypt}" + hashPassword(seller.getPassword())); // cannot find passwordencoder if bcrypt is
																				// not prefixed
		seller.setRoles(Arrays.asList(roleRepository.findByname("ROLE_USER")));
		seller.setEnabled(1);

		userRepository.save(seller);
		roleRepository.insertIntoAuthorities(seller.getUsername(), "ROLE_USER"); // check RoleRepository for reference
	}
}
