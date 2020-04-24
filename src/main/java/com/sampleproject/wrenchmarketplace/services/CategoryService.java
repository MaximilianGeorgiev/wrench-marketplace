package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import com.sampleproject.wrenchmarketplace.entities.Category;
import com.sampleproject.wrenchmarketplace.entities.ImageRoute;

public interface CategoryService {
	
	public List<Category> findAll();

	public Optional<Category> findById(int theId);
	
	public Optional<Category> findByname(String name);

	public void save(Category category);

	public void deleteById(int theId);
}
