package com.sampleproject.wrenchmarketplace.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sampleproject.wrenchmarketplace.entities.ImageRoute;
import com.sampleproject.wrenchmarketplace.entities.Listing;
import com.sampleproject.wrenchmarketplace.entities.User;
import com.sampleproject.wrenchmarketplace.services.ImageService;
import com.sampleproject.wrenchmarketplace.services.ListingService;
import com.sampleproject.wrenchmarketplace.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserService userService;

	public UserController(UserService userService, ImageService imageService) {
		this.userService = userService;
	}
	
	
	/* If the logged in user is the same one as the profile that is being viewed (in viewProfile())
	 * Then the user will have the option in the thymeleaf view to edit their profile
	 * Check ListingController.java for reference
	 */
	private boolean loggedInUserOwnsProfile(Authentication loggedInUser, int userID) {
		String loggedInUserName = loggedInUser.getName();
		int loggedInUserID = userService.findByusername(loggedInUserName).get().getId();

		if (loggedInUserID == userID) {
			return true;
		}
		return false;
	}

	@GetMapping("/createNewUser")
	public String createNewUser(Model theModel) {
		User user = new User();

		theModel.addAttribute("user", user);

		return "createNewUser";
	}

	@PostMapping("/saveNewUser")
	public String saveNewUser(@Valid @ModelAttribute("user") User theUser, BindingResult theBindingResult) {
		/*
		 * Valid constraint validator passes info to BindingResult and if any error on
		 * any fields is present then return the view again Thymeleaf will display the
		 * message and will inform the user what is wrong
		 */

		if (theBindingResult.hasErrors()) {
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

	@PostMapping("/viewUser")
	public String viewUser(@RequestParam("Id") String Id, Model theModel) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		int userID = Integer.parseInt(Id);
		
		theModel.addAttribute("user", userService.findById(userID).get());
		theModel.addAttribute("isOwner", loggedInUserOwnsProfile(loggedInUser, userID));
		
		return "viewuser";
	}
	
	@GetMapping("/editUser/{Id}")
	public String editUser(@PathVariable("Id") String Id, Model theModel) {
		theModel.addAttribute("user", userService.findById(Integer.parseInt(Id)));

		return "editUser";
	}

	@PostMapping("/saveEdit")
	public String saveEdit(@ModelAttribute("user") User editedUser, Model theModel, BindingResult theBindingResult) {
		User savedUser = userService.findById(editedUser.getId()).get();

		int userID = savedUser.getId();

		if (!editedUser.getPassword().equals(savedUser.getPassword())) {
			userService.editPassword(userID, editedUser.getPassword());
		}

		if (!editedUser.getFirstName().equals(savedUser.getFirstName())) {
			userService.editFirstName(userID, editedUser.getFirstName());
		}

		if (!editedUser.getSecondName().equals(savedUser.getSecondName())) {
			userService.editSecondName(userID, editedUser.getSecondName());
		}

		if (!editedUser.getEmail().equals(savedUser.getEmail())) {
			userService.editEmail(userID, editedUser.getEmail());
		}

		if (editedUser.getAge() != savedUser.getAge()) {
			userService.editAge(userID, editedUser.getAge());
		}

		return "redirect:/users/viewUser/" + String.valueOf(userID);
	}
}
