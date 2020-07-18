package com.sampleproject.wrenchmarketplace.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

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
	private CategoryService categoryService;

	ListingController(ListingService listingService, UserService userService, ImageService imageService,
			CategoryService categoryService) {
		this.listingService = listingService;
		this.userService = userService;
		this.imageService = imageService;
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

		if (!userService.isUserLoggedIn(loggedInUser)) {
			return false;
		}

		int loggedInUserID = userService.findByusername(loggedInUserName).get().getId();

		if (userService.findByListingIdInJoinedTable(loggedInUserID, listingID)) {
			return true;
		}
		return false;
	}

	/*
	 * Often used function, the user model is required for the nav bar of every
	 * single html view
	 */
	private User retrieveUserModel() {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User user = new User();

		if (!userService.isUserLoggedIn(loggedInUser)) {
			return new User();
		}

		user = userService.findByusername(loggedInUser.getName()).get();

		return user;
	}

	/*
	 * Looks in LISTING_IMAGE to find all IDs which are paired with this listingId
	 * Then from the IMAGE table I fetch all the paths of these ImageIds Then bind
	 * the data with the model so the pictures can be displayed in viewlisting
	 */
	public List<ImageRoute> getImageRoutesForListing(int listingID) {
		List<Integer> correspondingRouteIDs = imageService.findByListingIdInJoinedTable(listingID);
		List<ImageRoute> imageRoutes = new ArrayList<>();

		correspondingRouteIDs.forEach(r -> imageRoutes.add(imageService.findById(r).get()));

		return imageRoutes;
	}

	public HashMap<Listing, ImageRoute> searchListingByCriteria(List<Listing> allListings, String criteria,
			String searchWord) {
		HashMap<Listing, ImageRoute> listingsAndThumbnails = new LinkedHashMap<>();

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

		/*
		 * Get all the listings and iterate through them. If the search word is
		 * contained in either the description or the title (case insensitive). Then
		 * this is a valid match and will be added to the hashmap. There is a check
		 * whether there any images present, and if there are not then attach the
		 * "NO IMAGE FOUND" picture. I fetch the first picture and use it as a
		 * thumbnail. The searching can be improved with a regex pattern later on
		 */

		if (criteria.equalsIgnoreCase("standard")) {
			allListings.forEach(l -> {
				if (l.getTitle().toLowerCase().contains(searchWord.toLowerCase().trim())
						|| l.getDescription().toLowerCase().contains(searchWord.toLowerCase().trim())) {

					if (!getImageRoutesForListing(l.getId()).isEmpty()) {
						ImageRoute thumbnail = getImageRoutesForListing(l.getId()).get(0);
						listingsAndThumbnails.put(l, thumbnail);
					} else {
						listingsAndThumbnails.put(l,
								imageService.findByImageRoute("http://127.0.0.1:8033/no_image_found.jpg").get());
					}
				}
			});
		}

		/*
		 * If in USER_LISTING there is a valid pair of the logged in User ID and the
		 * listing ID then the user owns the listing and will be added to the hashmap
		 */
		else if (criteria.equalsIgnoreCase("userlistings")) {
			allListings.forEach(l -> {
				if (loggedInUserOwnsListing(loggedInUser, l.getId())) {
					if (!getImageRoutesForListing(l.getId()).isEmpty()) {
						ImageRoute thumbnail = getImageRoutesForListing(l.getId()).get(0);
						listingsAndThumbnails.put(l, thumbnail);
					} else {
						listingsAndThumbnails.put(l,
								imageService.findByImageRoute("http://127.0.0.1:8033/no_image_found.jpg").get());
					}
				}
			});
		} else if (criteria.equalsIgnoreCase("category")) {
			allListings.forEach(l -> {
				if (l.getCategory().equalsIgnoreCase(searchWord)) {
					if (!getImageRoutesForListing(l.getId()).isEmpty()) {
						ImageRoute thumbnail = getImageRoutesForListing(l.getId()).get(0);
						listingsAndThumbnails.put(l, thumbnail);
					} else {
						listingsAndThumbnails.put(l,
								imageService.findByImageRoute("http://127.0.0.1:8033/no_image_found.jpg").get());
					}
				}
			});
		} else if (criteria.equalsIgnoreCase("watched")) {
			if (userService.isUserLoggedIn(loggedInUser)) {
				int userID = userService.findByusername(loggedInUser.getName()).get().getId();

				allListings.forEach(l -> {
					if (userService.isUserWatchingListingId(userID, l.getId())) {
						if (!getImageRoutesForListing(l.getId()).isEmpty()) {
							ImageRoute thumbnail = getImageRoutesForListing(l.getId()).get(0);
							listingsAndThumbnails.put(l, thumbnail);
						} else {
							listingsAndThumbnails.put(l,
									imageService.findByImageRoute("http://127.0.0.1:8033/no_image_found.jpg").get());
						}
					}
				});
			}
		}

		return listingsAndThumbnails;
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
		theModel.addAttribute("user", retrieveUserModel());

		return "createNewListing";
	}

	@PostMapping("/saveNewListing")
	public String saveNewListing(@Valid @ModelAttribute("listing") Listing theListing,
			@RequestParam("files") MultipartFile[] files, BindingResult theBindingResult) throws IOException {
		/*
		 * Fetch current user, since it is another object from our User entity I search
		 * in the database by name and then manually set the listing's seller to this
		 * User Upload file, insert into IMAGE database a new ImageRoute. Then insert
		 * into LISTING_IMAGE the following pair: {listing_id, image_id}. Also insert
		 * into USER_LISTING the following pair: {user_id, listing_id}
		 */
		
		// Validates fields
		if (theBindingResult.hasErrors()) {
			return "createNewListing";
		}
		
		listingService.save(theListing);

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				break;
			}

			String uploadedFileName = imageService.handleFileUpload(file);
			
			/* handleFileUpload returns null in two cases:
			 * 1. The uploaded file already exists (needed for editlisting)
			 * 2. The uploaded file does not meet the required format (here)
			 */
			if (uploadedFileName == null) {
				listingService.deleteById(theListing.getId());
				return "redirect:/";
			}

			try {
				imageService.insertIntoJoinedTable(theListing.getId(),
						imageService.findByImageRoute(uploadedFileName).get().getId());
			} catch (Exception e) {
				return "createNewListing";
			}
		}

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findByusername(loggedInUser.getName()).get();
		
		userService.insertIntoUserListingJoinedTable(currentUser.getId(), theListing.getId());

		return "redirect:/";
	}

	@PostMapping("/deleteListing")
	public String deleteListing(@RequestParam("listingId") String passedId) {
		Integer listingId = Integer.parseInt(passedId);

		/*
		 * Not sure whether this code is good practise, pretty sure it is not and will
		 * be addressed After the completion of the project. First I delete the IDs
		 * present in USER_LISTING and LISTING_IMAGE. And then I delete it from LISTING
		 * as well, but I am pretty sure that cascading should delete from the joined
		 * tables automatically, which doesn't happen in this case
		 */

		listingService.deleteListingFromListingImageJoinedTable(listingId);
		listingService.deleteListingFromUserListingJoinedTable(listingId);
		listingService.deleteById(listingId);

		return "redirect:/";
	}

	@GetMapping("/viewListing")
	public String viewListing(HttpServletRequest request, Model theModel) {
		String Id = request.getQueryString().substring(request.getQueryString().lastIndexOf("=") + 1,
				request.getQueryString().lastIndexOf("=") + 2);

		Listing listing = listingService.findById(Integer.parseInt(Id)).get();
		int listingID = listing.getId();
		boolean isUserLoggedIn = false;

		List<ImageRoute> imageRoutes = getImageRoutesForListing(listingID);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		isUserLoggedIn = (userService.isUserLoggedIn(loggedInUser)) ? true : false;

		/*
		 * Why is this so spaghetti code?
		 * 
		 * /@watch below inserts into USER_WATCHEDLISTING table a {USER_ID, LISTING_ID}
		 * pair Then it redirects back to this @/viewListing page Here I query the table
		 * to check if this listing is in the user's watched list, they will get the
		 * option to remove from watchlist. Basically a two way toggle: 1) to remove the
		 * listing from watchling 2) to add to watchlist
		 *
		 * @/viewListing is loaded before /@watch list and the isWatched variable will
		 * be null if it is not handled here. That's why it's not set in /@watch
		 */
		if (isUserLoggedIn) {
			int userID = userService.findByusername(loggedInUser.getName()).get().getId();

			if (userService.isUserWatchingListingId(userID, listingID)) {
				theModel.addAttribute("isWatched", true);
			} else {
				theModel.addAttribute("isWatched", false);
			}
		} else {
			theModel.addAttribute("isWatched", false);
		}

		theModel.addAttribute("listing", listing);
		theModel.addAttribute("imageRoutes", imageRoutes);
		theModel.addAttribute("isUserLoggedIn", isUserLoggedIn);
		theModel.addAttribute("isOwner", loggedInUserOwnsListing(loggedInUser, listingID));
		theModel.addAttribute("user", retrieveUserModel());

		return "viewlisting";
	}

	@GetMapping("/watch")
	public String toggleWatchList(HttpServletRequest request, Model theModel) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findByusername(loggedInUser.getName()).get();

		String listingIdURL = request.getQueryString().toString().substring(request.getQueryString().indexOf("=") + 1);
		int listingId = Integer.parseInt(listingIdURL);
		int userId = currentUser.getId();

		/* if it's watched then remove from watch list */
		if (userService.isUserWatchingListingId(userId, listingId)) {
			userService.deleteFromWatchListJoinedTable(currentUser.getId());
		} else {
			/*
			 * The user definitely exists, otherwise they wouldn't have a button to @/watch
			 * (It's handled in the viewlisting.html) Add to USER_WATCHLING JOIN TABLE
			 */
			userService.insertIntoWatchListJoinedTable(currentUser.getId(), listingId);
		}

		return "redirect:/listings/viewListing/" + String.valueOf(listingId);
	}

	/*
	 * The search view is the same for: using the search bar, clicking on
	 * "Your listings", searching by category from the index and also loading up
	 * your watched list. So why not have one search function for all of them? POST
	 * mapping since the GET mapping appends the search word differently when the
	 * field is plain text and when the field is a bean property and makes it hard
	 * to fetch as a PathVariable that's why it is posted as a requestparam. The
	 * searchListingByCriteria function will return all the listings necessary to
	 * display, along with their image routes and this will be bound with the model.
	 */

	@GetMapping("/search/**")
	public String search(HttpServletRequest request, Model theModel) {

		/*
		 * URL format:
		 * http://127.0.0.1:8080/listings/search/standard?searchWord=asdf&type=standard
		 */
		String searchWord = request.getQueryString().substring(request.getQueryString().indexOf("=") + 1,
				request.getQueryString().indexOf("&"));
		String type = request.getQueryString().substring(request.getQueryString().lastIndexOf("=") + 1);

		/*
		 * Spaces are appended with a plus sign whereas in the database info is stored
		 * with spaces that's why plus must be replaced with space
		 */
		searchWord = (searchWord.contains("+")) ? searchWord.replace("+", " ") : searchWord;

		List<Listing> listingsFound = listingService.findAll();
		HashMap<Listing, ImageRoute> listingsAndThumbnails = searchListingByCriteria(listingsFound, type, searchWord);

		theModel.addAttribute("listingsAndThumbnails", listingsAndThumbnails);
		theModel.addAttribute("listingsMatchesCount", listingsAndThumbnails.size());
		theModel.addAttribute("user", retrieveUserModel());

		return "search";
	}

	@GetMapping("/edit/**")
	public String editListing(HttpServletRequest request, Model theModel) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

		/*
		 * URL format: http://127.0.0.1:8080/users/editListing?userID=1&listingID=1 Must
		 * be fixed when there are more than 10 users
		 */
		String listingID = request.getQueryString().substring(request.getQueryString().lastIndexOf("=") + 1,
				request.getQueryString().lastIndexOf("=") + 2);

		/* No unauthorised edits */
		if (!userService.isUserLoggedIn(loggedInUser)
				|| !this.loggedInUserOwnsListing(loggedInUser, Integer.parseInt(listingID))) {
			return "redirect:/";
		}

		theModel.addAttribute("listing", listingService.findById(Integer.parseInt(listingID)).get());
		theModel.addAttribute("imageRoutes", getImageRoutesForListing(Integer.parseInt(listingID)));
		theModel.addAttribute("user", retrieveUserModel());

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
		
		/* image handling */
		for (MultipartFile file : files) {
			if (files.length == 0) {
				break;
			}
			
			String uploadedFileName = imageService.handleFileUpload(file);
			
			/* imageServiceImpl returns null if this image route is found in the database;
			 * avoiding duplicate uploads and randomly appearing new routes
			 */
			if (uploadedFileName.equals(null)) {
				break;
			}
			
			/* If the user is not trying to upload the same file as before, then it will be uploaded */
			imageService.insertIntoJoinedTable(theListing.getId(),
					imageService.findByImageRoute(uploadedFileName).get().getId());
			
		}

		return "redirect:/listings/viewListing?Id=" + theListing.getId();

	}
	
	/* Remove image is a href from editListing.html view; deletes the images from IMAGE table and LISTING_IMAGE table and returns
	 * back the edit view so the customer can continue with the editing 
	 * When the user clicks on the image it gets removed and the remaining images are updated real time */
	@GetMapping("/removeImage")
	public String removeImageOnListingEdit(@ModelAttribute("listing") Listing theListing, HttpServletRequest request, Model theModel) {
		String imageRoute = request.getQueryString().substring(request.getQueryString().indexOf("=") + 1,
				request.getQueryString().indexOf("&"));
		
		int listingID = Integer.parseInt(request.getQueryString().substring(request.getQueryString().lastIndexOf("=") + 1));	
		int imageID = imageService.findByImageRoute(imageRoute).get().getId();
		
		listingService.deleteImageFromListingImageJoinedTable(imageID);
		imageService.deleteById(imageID);
		
		User user = retrieveUserModel();
	
		theModel.addAttribute("listing", theListing);
		theModel.addAttribute("imageRoutes", getImageRoutesForListing(theListing.getId()));
		theModel.addAttribute("user", user);
		return "redirect:/listings/edit?userID=" + user.getId() + "&listingID=" + listingID;
	}
}
