package com.sampleproject.wrenchmarketplace.services;



import java.util.List;
import java.util.Optional;

import com.sampleproject.wrenchmarketplace.entities.Listing;

public interface ListingService {
	
	public List<Listing> findAll();
	
	public Optional<Listing> findById(int theId);
	
	public void save(Listing listing);
	
	public void deleteById(int theId);

}
