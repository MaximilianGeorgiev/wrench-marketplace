package com.sampleproject.wrenchmarketplace.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import com.sampleproject.wrenchmarketplace.entities.*;
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
	
	
	public MainController (UserController userController) {
		this.userController = userController;
	}

	@GetMapping("/")
	public String showMainPageUnlogged(Model theModel) {
		return "mainpage_unlogged";
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
		
		return "redirect:/logged";
	}
	
	@GetMapping("/logged")
	public String showMainPageLogged(Model theModel) {
		return "mainpage_logged";
	}
	
}
