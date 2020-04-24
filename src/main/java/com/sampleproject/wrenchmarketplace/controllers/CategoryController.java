package com.sampleproject.wrenchmarketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sampleproject.wrenchmarketplace.entities.Category;
import com.sampleproject.wrenchmarketplace.services.CategoryService;

@Controller
public class CategoryController {
	private CategoryService categoryService;
	
	public CategoryController (CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	/* prepouplated in data.sql */
	@Deprecated
	@GetMapping("/test")
	public void createPresetCategories() {
		categoryService.save(new Category("Car parts"));
	}

}
