package com.sampleproject.wrenchmarketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sampleproject.wrenchmarketplace.entities.User;
import com.sampleproject.wrenchmarketplace.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/createNewUser")
	public String createNewUser(Model theModel) {
		User user = new User();
		
		theModel.addAttribute("user", user);
		
		return "createNewUser";
	}
	
	@PostMapping("/saveNewUser")
	public String saveNewUser(@ModelAttribute("user") User theUser) {
		
		// if user with this email or username exists then it will redirect back to the creation form
		//if (userService.findByUserName(theUser.getUsername()) != null) {
		//	return "redirect:/createNewUser";
	//	}
		
		//otherwise push to db 
		userService.save(theUser);
		return "redirect:/";	
	}
}
