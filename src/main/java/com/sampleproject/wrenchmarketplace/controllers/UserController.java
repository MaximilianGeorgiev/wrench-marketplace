package com.sampleproject.wrenchmarketplace.controllers;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String saveNewUser(@Valid @ModelAttribute("user") User theUser, BindingResult theBindingResult) {
		/* Valid constraint validator passes info to BindingResult and if any error on any fields is present then return the view again 
		 * Thymeleaf will display the message and will inform the user what is wrong
		 */
		
		 if (theBindingResult.hasErrors()){
			 return "createNewUser";
		 }

		// if user with this email or username exists then it will redirect back to the
		// creation form
		Optional<User> searchResultUserName = userService.findByusername(theUser.getUsername());
		Optional<User> searchResultEmail = userService.findByemail(theUser.getEmail());

		if (searchResultUserName.isPresent() || searchResultEmail.isPresent()) {
			return "redirect:/users/createNewUser";
		}

		// otherwise push to db
		userService.save(theUser);
		return "redirect:/";
	}

	public User verifyLogin(String username) {
		Optional<User> user = userService.findByusername(username);

		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

}
