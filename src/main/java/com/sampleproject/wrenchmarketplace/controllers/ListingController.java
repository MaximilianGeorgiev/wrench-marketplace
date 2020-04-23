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
import com.sampleproject.wrenchmarketplace.entities.ImageRoute;
import com.sampleproject.wrenchmarketplace.entities.Listing;
import com.sampleproject.wrenchmarketplace.entities.User;
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

	ListingController(ListingService listingService, UserService userService, ImageService imageService,
			ImageController imageController) {
		this.listingService = listingService;
		this.userService = userService;
		this.imageService = imageService;
		this.imageController = imageController;
	}

	@GetMapping("/createNewListing")
	public String createNewListing(Model theModel) {
		Listing listing = new Listing();

		theModel.addAttribute("listing", listing);

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
		 */

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findByusername(loggedInUser.getName()).get();
		theListing.setSeller(currentUser);
		listingService.save(theListing);
		
		
		for (MultipartFile file : files) {
		String uploadedFileName = imageController.handleFileUpload(file);
		imageService.insertIntoJoinedTable(theListing.getId(),
				imageService.findByimageRoute(uploadedFileName).get().getId());
		
		}
	
		return "redirect:/";
	}

	@GetMapping("/viewListing/{Id}")
	public String viewListing(@PathVariable String Id, Model theModel) {
		Listing listing = listingService.findById(Integer.parseInt(Id)).get();

		/*
		 * Looks in LISTING_IMAGE to find all IDs which are paired with this listingId
		 * Then from the IMAGE table I fetch all the paths of these ImageIds Then bind
		 * the data with the model so the pictures can be displayed in viewlisting
		 */
		List<Integer> correspondingRouteIDs = imageService.findByListingIdInJoinedTable(listing.getId());
		List<ImageRoute> imageRoutes = new ArrayList<>();
		
		correspondingRouteIDs.forEach(r -> imageRoutes.add(imageService.findById(r).get()));

		theModel.addAttribute("listing", listing);
		theModel.addAttribute("imageRoutes", imageRoutes);

		return "viewlisting";
	}
}
