package com.sampleproject.wrenchmarketplace.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.sampleproject.wrenchmarketplace.entities.*;
import com.sampleproject.wrenchmarketplace.services.CategoryService;
import com.sampleproject.wrenchmarketplace.services.UserService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MainController {
	private UserService userService;
	private CategoryService categoryService;

	public MainController(UserService userService, CategoryService categoryService) {
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@GetMapping("/")
	public String showMainPage(Model theModel) {
		/*
		 * get authentication object then get the username of the logged in user and
		 * search in the database for an User object with the same username. A user is
		 * passed to the model as it is needed for various api calls for example if the
		 * client clicks on settings it will take them to /users/edituser/{Id} which is
		 * the Id of the logged in user
		 */

		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();

		// Authentication cannot be null and if there is no user logged in then the
		// principal is anonymousUser
		if (!loggedUser.getPrincipal().equals("anonymousUser")) {
			User user = userService.findByusername(loggedUser.getName()).get();
			theModel.addAttribute("user", user);
		}
		
		theModel.addAttribute("categories", categoryService.findAll());
		return "index";
	}

	@GetMapping("/showLogin")
	public String showLoginPage(Model theModel) {
		return "login";
	}

}
