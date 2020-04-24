package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sampleproject.wrenchmarketplace.dao.CategoryRepository;
import com.sampleproject.wrenchmarketplace.entities.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(int theId) {
		return categoryRepository.findById(theId);
	}

	@Override
	public Optional<Category> findByname(String name) {
		 return categoryRepository.findByname(name);
	}


	@Override
	public void save(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void deleteById(int theId) {
		categoryRepository.deleteById(theId);
	}

}
