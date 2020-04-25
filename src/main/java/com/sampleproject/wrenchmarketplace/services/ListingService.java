package com.sampleproject.wrenchmarketplace.services;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sampleproject.wrenchmarketplace.entities.Listing;

public interface ListingService {
	
	public void deleteListingFromListingImageJoinedTable(Integer listing_ID);
	
	public void deleteListingFromUserListingJoinedTable(Integer listing_ID);
	
	public List<Listing> findAll();
	
	public Optional<Listing> findById(int theId);
	
	public List<Listing> findBytitle(String title);
	
	public void save(Listing listing);
	
	public void deleteById(int theId);

}
