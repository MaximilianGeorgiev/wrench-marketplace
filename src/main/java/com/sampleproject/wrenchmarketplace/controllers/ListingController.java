package com.sampleproject.wrenchmarketplace.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.sampleproject.wrenchmarketplace.entities.Category;
import com.sampleproject.wrenchmarketplace.entities.ImageRoute;
import com.sampleproject.wrenchmarketplace.entities.Listing;
import com.sampleproject.wrenchmarketplace.entities.User;
import com.sampleproject.wrenchmarketplace.services.CategoryService;
import com.sampleproject.wrenchmarketplace.services.ImageService;
import com.sampleproject.wrenchmarketplace.services.ListingService;
import com.sampleproject.wrenchmarketplace.services.UserService;

@Controller
@RequestMapping("/listings")
public class ListingController {
	private ListingService listingService;
	private UserService userService;
	private ImageService imageService;
	private ImageController imageController;
	private CategoryService categoryService;

	ListingController(ListingService listingService, UserService userService, ImageService imageService,
			ImageController imageController, CategoryService categoryService) {
		this.listingService = listingService;
		this.userService = userService;
		this.imageService = imageService;
		this.imageController = imageController;
		this.categoryService = categoryService;
	}

	@GetMapping("/createNewListing")
	public String createNewListing(Model theModel) {
		Listing listing = new Listing();
		List<Category> categories = categoryService.findAll();
		
		/* Prepopulate category options by loading them from db and passing the model to thymeleaf */

		theModel.addAttribute("listing", listing);
		theModel.addAttribute("categories", categories);

		return "createNewListing";
	}

	@PostMapping("/saveNewListing")
	public String saveNewListing(@ModelAttribute("listing") Listing theListing,
			@RequestParam("files") MultipartFile[] files) throws IOException {
		/*
		 * Fetch current user, since it is another object from our User entity I search
		 * in the database by name and then manually set the listing's seller to this
		 * User Upload file, insert into IMAGE database a new ImageRoute Then insert
		 * into LISTING_IMAGE the following pair: {listing_id, image_id}
		 * Also insert into USER_LISTING the following pair: {user_id, listing_id}
		 */

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findByusername(loggedInUser.getName()).get();
		listingService.save(theListing);
		
		userService.insertIntoJoinedTable(currentUser.getId(), theListing.getId());
		
		for (MultipartFile file : files) {
		String uploadedFileName = imageController.handleFileUpload(file);
		imageService.insertIntoJoinedTable(theListing.getId(),
				imageService.findByimageRoute(uploadedFileName).get().getId());
		}
	
		return "redirect:/";
	}
	
	@PostMapping("/deleteListing")
	public String deleteListing(@ModelAttribute("listing") Listing theListing) {
		
		int listingId = theListing.getId();
		
		listingService.deleteById(listingId);
		
		return "redirect:/";
	}
	

	@GetMapping("/viewListing/{Id}")
	public String viewListing(@PathVariable String Id, Model theModel) {
		Listing listing = listingService.findById(Integer.parseInt(Id)).get();
		int listingID = listing.getId();

		/*
		 * Looks in LISTING_IMAGE to find all IDs which are paired with this listingId
		 * Then from the IMAGE table I fetch all the paths of these ImageIds Then bind
		 * the data with the model so the pictures can be displayed in viewlisting
		 */
		List<Integer> correspondingRouteIDs = imageService.findByListingIdInJoinedTable(listingID);
		List<ImageRoute> imageRoutes = new ArrayList<>();
		
		correspondingRouteIDs.forEach(r -> imageRoutes.add(imageService.findById(r).get()));

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		
		theModel.addAttribute("listing", listing);
		theModel.addAttribute("imageRoutes", imageRoutes);
		theModel.addAttribute("isOwner", loggedInUserOwnsListing(loggedInUser, listingID));

		return "viewlisting";
	}
	
	
	/*
	 * Checks in the USER_LISTING table to see whether there is a match of the current user and the current listing
	 * Returns true if logged user owns this listing and false if not
	 * This boolean will be passed to the thyemeleaf model where the client will have the option to remove their listing
	 * if the listing is uploaded by them
	 * Check UserRepository for more info
	 */
	public boolean loggedInUserOwnsListing(Authentication loggedInUser, int listingID) {
			String loggedInUserName = loggedInUser.getName();
			int loggedInUserID = userService.findByusername(loggedInUserName).get().getId();
			
			if (userService.findByListingIdInJoinedTable(loggedInUserID, listingID)){
				return true;
			}
		return false;
	}
}
