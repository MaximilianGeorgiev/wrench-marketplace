package com.sampleproject.wrenchmarketplace.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampleproject.wrenchmarketplace.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
