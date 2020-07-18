package com.sampleproject.wrenchmarketplace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sampleproject.wrenchmarketplace.dao.ListingRepository;
import com.sampleproject.wrenchmarketplace.entities.Listing;

/* class delegates calls to repository
 * implementation of services allows easier modifications of the code
 * and is more customizable; multiple services can be used
 */

@Service
public class ListingServiceImpl implements ListingService {
	private ListingRepository listingRepository;

	@Autowired
	public ListingServiceImpl(ListingRepository listingRepository2) {
		listingRepository = listingRepository2;
	}

	@Override
	public void editTitle(Integer Id, String title) {
		listingRepository.editTitle(Id, title);
	}

	@Override
	public void editPrice(Integer Id, Double price) {
		listingRepository.editPrice(Id, price);
	}

	@Override
	public void editDescription(Integer Id, String description) {
		listingRepository.editDescription(Id, description);
	}
	
	@Override
	public void editPhoneNumber(Integer Id, String phoneNumber) {
		listingRepository.editPhoneNumber(Id, phoneNumber);
	}

	@Override
	public void deleteListingFromListingImageJoinedTable(Integer listing_ID) {
		listingRepository.deleteListingFromListingImageJoinedTable(listing_ID);
	}
	
	@Override
	public void deleteImageFromListingImageJoinedTable(@Param("image_ID") Integer image_ID) {
		listingRepository.deleteImageFromListingImageJoinedTable(image_ID);
	}

	@Override
	public void deleteListingFromUserListingJoinedTable(Integer listing_ID) {
		listingRepository.deleteListingFromUserListingJoinedTable(listing_ID);
	}

	@Override
	public List<Listing> findAll() {
		return listingRepository.findAll();
	}

	@Override
	public Optional<Listing> findById(int theId) {
		return listingRepository.findById(theId);
	}

	@Override
	public List<Listing> findBytitle(String title) {
		return listingRepository.findBytitle(title);
	}
	
	@Override
	public List<Listing> findListingsByUserId(Integer user_ID){
		List<Integer> listingIDsFound = listingRepository.findListingIDsByUserID(user_ID);
		List<Listing> returnListings = new ArrayList<>();
		
		for (Integer ID : listingIDsFound) {
			returnListings.add(listingRepository.findById(ID).get());
		}
		
		return returnListings;
	}

	@Override
	public void save(Listing listing) {
		listingRepository.save(listing);
	}

	@Override
	public void deleteById(int theId) {
		listingRepository.deleteById(theId);
	}
}
