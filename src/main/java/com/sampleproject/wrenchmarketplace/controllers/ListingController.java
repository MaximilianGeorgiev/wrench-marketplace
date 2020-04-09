package com.sampleproject.wrenchmarketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sampleproject.wrenchmarketplace.entities.Listing;
import com.sampleproject.wrenchmarketplace.services.ListingService;

@Controller
@RequestMapping("/listings")
public class ListingController {
  private ListingService listingService;
  
  ListingController (ListingService listingService){
	  this.listingService = listingService;
  }
  
  @GetMapping("/createNewListing")
  public String createNewListing(Model theModel) {
	  Listing listing = new Listing();
	  
	  theModel.addAttribute("listing", listing);
	  
	  return "createNewListing";
  }
  
  @PostMapping("/saveNewListing")
  public String saveNewListing(@ModelAttribute("listing") Listing theListing) {
	  listingService.save(theListing);
	  
	  return "redirect:/logged";
  }
}
