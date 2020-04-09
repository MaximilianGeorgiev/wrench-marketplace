package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Listing> findAll() {
		return listingRepository.findAll();
	}

	@Override
	public Optional<Listing> findById(int theId) {
		return listingRepository.findById(theId);
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
