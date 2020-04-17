package com.sampleproject.wrenchmarketplace.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampleproject.wrenchmarketplace.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	public Role findRoleByName(String theRoleName);

}
