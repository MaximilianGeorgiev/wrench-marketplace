package com.sampleproject.wrenchmarketplace.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sampleproject.wrenchmarketplace.dao.UserRepository;
import com.sampleproject.wrenchmarketplace.entities.Listing;
import com.sampleproject.wrenchmarketplace.entities.User;
import com.sampleproject.wrenchmarketplace.services.ListingService;
import com.sampleproject.wrenchmarketplace.services.UserService;

@Controller
@RequestMapping("/listings")
public class ListingController {
	private ListingService listingService;
	private UserService userService;

	ListingController(ListingService listingService, UserService userService) {
		this.listingService = listingService;
		this.userService = userService;
	}

	@GetMapping("/createNewListing")
	public String createNewListing(Model theModel) {
		Listing listing = new Listing();

		theModel.addAttribute("listing", listing);

		return "createNewListing";
	}

	@PostMapping("/saveNewListing")
	public String saveNewListing(@ModelAttribute("listing") Listing theListing, @RequestParam("image") MultipartFile image)
			throws IOException {
		/*
		 * Fetch current user, since it is another object from our User entity I search
		 * in the database by name and then manually set the listing's seller to this
		 * User
		 */

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findByusername(loggedInUser.getName()).get();
		theListing.setSeller(currentUser);
		
		theListing.setImage(image.getBytes());
		
		listingService.save(theListing);

		return "redirect:/";
	}

	@GetMapping("/viewListing/{Id}")
	public String viewListing(@PathVariable String Id, Model theModel) {
		Listing listing = listingService.findById(Integer.parseInt(Id)).get();
		theModel.addAttribute("listing", listing);

		return "viewlisting";
	}
}
