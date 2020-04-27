package com.sampleproject.wrenchmarketplace.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.sampleproject.wrenchmarketplace.entities.*;
import com.sampleproject.wrenchmarketplace.services.UserService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/")
@Controller
public class MainController {
	private UserController userController;
	private UserService userService;
	
	public MainController (UserController userController, UserService userService) {
		this.userController = userController;
		this.userService = userService;
	}

	@GetMapping("/")
	public String showMainPage(Model theModel) {
		
		/* get authentication object then get the username of the logged in user
		 * and search in the database for an User object with the same username
		 * A user is passed to the model as it is needed for various api calls for example
		 * if the client clicks on settings it will take them to /users/edituser/{Id} which is the
		 * Id of the logged in user
		 */
		
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		
		// Authentication cannot be null and if there is no user logged in then the principal is anonymousUser
		if (!loggedUser.getPrincipal().equals("anonymousUser")) {
		User user = userService.findByusername(loggedUser.getName()).get();
		theModel.addAttribute("user", user);
		}
		return "index";
	}
	
	@GetMapping("/showLogin")
	public String showLoginPage(Model theModel) {
		return "login";
	}
	
	@PostMapping("/verifyLogin")
	public String verifyLogin(@RequestParam(name = "username") String username, @RequestParam(name= "password") String password) {
		/*
		 * I am not passing a user object with thymeleaf but rather plain string from the html form
		 * I check with the controller if there is such an username in the database and if there is such
		 * we will check if the encrypted password in the db matches the one in the html form 
		 * and then proceed to login
		 * if not redirect to login page again
		 */
		
		User user = userController.verifyLogin(username);
		
		if (user == null) {
			return "redirect:/showLogin";	
		}
		
		if (!BCrypt.checkpw(password, user.getPassword())) {
			return "redirect:/showLogin";	
		}
		
		return "redirect:/";
	}
}
