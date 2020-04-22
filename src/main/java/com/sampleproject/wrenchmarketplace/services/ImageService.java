package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sampleproject.wrenchmarketplace.entities.ImageRoute;
import com.sampleproject.wrenchmarketplace.entities.Listing;

public interface ImageService {
	
	public void insertIntoJoinedTable(Integer listing_id, Integer image_id);

	public List<ImageRoute> findAll();

	public Optional<ImageRoute> findById(int theId);
	
	public Optional<ImageRoute> findByimageRoute(String imageRoute);
	
	public List<ImageRoute> findByListingIdInJoinedTable (int listingId);

	public void save(ImageRoute listing);

	public void deleteById(int theId);
}
