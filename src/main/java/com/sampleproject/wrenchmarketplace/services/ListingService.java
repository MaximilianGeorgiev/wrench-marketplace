package com.sampleproject.wrenchmarketplace.services;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sampleproject.wrenchmarketplace.entities.Listing;

public interface ListingService {
	
	public void editTitle(Integer Id, String title);
	
	public void editPrice(Integer Id, Double price);
	
	public void editDescription(Integer Id, String description);
	
	public void editPhoneNumber(Integer Id, String phoneNumber);
	
	public void deleteListingFromListingImageJoinedTable(Integer listing_ID);
	
	public void deleteListingFromUserListingJoinedTable(Integer listing_ID);
	
	public List<Listing> findAll();
	
	public Optional<Listing> findById(int theId);
	
	public List<Listing> findBytitle(String title);
	
	public List<Listing> findListingsByUserId(Integer user_ID);
	
	public void save(Listing listing);
	
	public void deleteById(int theId);

}
