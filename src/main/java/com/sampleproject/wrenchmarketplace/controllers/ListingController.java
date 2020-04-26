package com.sampleproject.wrenchmarketplace.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/*
	 * Checks in the USER_LISTING table to see whether there is a match of the
	 * current user and the current listing Returns true if logged user owns this
	 * listing and false if not This boolean will be passed to the thyemeleaf model
	 * where the client will have the option to remove their listing if the listing
	 * is uploaded by them Check UserRepository for more info
	 */
	private boolean loggedInUserOwnsListing(Authentication loggedInUser, int listingID) {
		String loggedInUserName = loggedInUser.getName();
		int loggedInUserID = userService.findByusername(loggedInUserName).get().getId();

		if (userService.findByListingIdInJoinedTable(loggedInUserID, listingID)) {
			return true;
		}
		return false;
	}

	/*
	 * Looks in LISTING_IMAGE to find all IDs which are paired with this listingId
	 * Then from the IMAGE table I fetch all the paths of these ImageIds Then bind
	 * the data with the model so the pictures can be displayed in viewlisting
	 */
	private List<ImageRoute> getImageRoutesForListing(int listingID) {
		List<Integer> correspondingRouteIDs = imageService.findByListingIdInJoinedTable(listingID);
		List<ImageRoute> imageRoutes = new ArrayList<>();

		correspondingRouteIDs.forEach(r -> imageRoutes.add(imageService.findById(r).get()));

		return imageRoutes;
	}

	@GetMapping("/createNewListing")
	public String createNewListing(Model theModel) {
		Listing listing = new Listing();
		List<Category> categories = categoryService.findAll();

		/*
		 * Prepopulate category options by loading them from db and passing the model to
		 * thymeleaf
		 */

		theModel.addAttribute("listing", listing);
		theModel.addAttribute("categories", categories);

		return "createNewListing";
	}

	@PostMapping("/saveNewListing")
	public String saveNewListing(@Valid @ModelAttribute("listing") Listing theListing,
			@RequestParam("files") MultipartFile[] files, BindingResult theBindingResult) throws IOException {
		/*
		 * Fetch current user, since it is another object from our User entity I search
		 * in the database by name and then manually set the listing's seller to this
		 * User Upload file, insert into IMAGE database a new ImageRoute Then insert
		 * into LISTING_IMAGE the following pair: {listing_id, image_id} Also insert
		 * into USER_LISTING the following pair: {user_id, listing_id}
		 */

		// Validates fields
		if (theBindingResult.hasErrors()) {
			return "createNewListing";
		}

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

	@GetMapping("/deleteListing/{Id}")
	public String deleteListing(@PathVariable("Id") String passedId) {

		Integer listingId = Integer.parseInt(passedId);

		/*
		 * Not sure whether this code is good practise, pretty sure it is not and will
		 * be addressed After the completion of the project First I delete the IDs
		 * present in USER_LISTING and LISTING_IMAGE And then I delete it from LISTING
		 * as well, but I am pretty sure that cascading should delete from the joined
		 * tables automatically, which doesn't happen in this case
		 */

		listingService.deleteListingFromListingImageJoinedTable(listingId);
		listingService.deleteListingFromUserListingJoinedTable(listingId);
		listingService.deleteById(listingId);

		return "redirect:/";
	}

	@GetMapping("/viewListing/{Id}")
	public String viewListing(@PathVariable("Id") String Id, Model theModel) {
		Listing listing = listingService.findById(Integer.parseInt(Id)).get();
		int listingID = listing.getId();

		List<ImageRoute> imageRoutes = getImageRoutesForListing(listingID);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

		theModel.addAttribute("listing", listing);
		theModel.addAttribute("imageRoutes", imageRoutes);
		theModel.addAttribute("isOwner", loggedInUserOwnsListing(loggedInUser, listingID));

		return "viewlisting";
	}

	@GetMapping("/search")
	public String search(@RequestParam(value = "searchWord") String searchWord, Model theModel) {
		List<Listing> listingsFound = listingService.findAll();
		List<Listing> listingsMatches = new ArrayList<>();

		/*
		 * Get all listings then in a new list change the title and the searchword to
		 * lower case Compare them, and if there is a match add to listingsMatches Can
		 * be improved with a regex pattern The count of the search will also be passed
		 * and in the view a message like "x Results found" will be displayed
		 */
		listingsFound.forEach(l -> {
			if (l.getTitle().toLowerCase().contains(searchWord.toLowerCase().trim())
					|| l.getDescription().toLowerCase().contains(searchWord.toLowerCase().trim())) {
				listingsMatches.add(l);
			}
		});

		int listingsMatchesCount = listingsMatches.size();

		System.out.println(listingsFound.size());
		System.out.println(listingsMatchesCount);

		theModel.addAttribute("listingsMatches", listingsMatches);
		theModel.addAttribute("listingsMatchesCount", listingsMatchesCount);

		return "search";
	}

	@GetMapping("/edit/{Id}")
	public String editListing(@PathVariable("Id") String Id, Model theModel) {
		theModel.addAttribute("listing", listingService.findById(Integer.parseInt(Id)).get());
		theModel.addAttribute("imageRoutes", getImageRoutesForListing(Integer.parseInt(Id)));

		return "editListing";
	}

	@PostMapping("/saveEdit")
	public String saveEdit(@ModelAttribute("listing") Listing theListing, @RequestParam("files") MultipartFile[] files,
			BindingResult theBindingResult) {
		Listing listingBeforeEdit = listingService.findById(theListing.getId()).get();

		if (!theListing.getTitle().equalsIgnoreCase(listingBeforeEdit.getTitle())) {
			listingService.editTitle(theListing.getId(), theListing.getTitle());
		}

		if (theListing.getPrice() != listingBeforeEdit.getPrice()) {
			listingService.editPrice(theListing.getId(), theListing.getPrice());
		}

		if (!theListing.getDescription().equalsIgnoreCase(listingBeforeEdit.getDescription())) {
			listingService.editDescription(theListing.getId(), theListing.getDescription());
		}

		// uploads new files and works but if the user decides to delete an image??
		for (MultipartFile file : files) {
			String uploadedFileName = imageController.handleFileUpload(file);
			imageService.insertIntoJoinedTable(theListing.getId(),
					imageService.findByimageRoute(uploadedFileName).get().getId());
		}

		return "redirect:/";
	}
}
