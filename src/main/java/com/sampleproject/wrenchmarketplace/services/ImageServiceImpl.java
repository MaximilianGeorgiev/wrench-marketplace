package com.sampleproject.wrenchmarketplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sampleproject.wrenchmarketplace.dao.ImageRepository;
import com.sampleproject.wrenchmarketplace.entities.ImageRoute;


@Service
public class ImageServiceImpl implements ImageService {
	private ImageRepository imageRepository;
	
	public ImageServiceImpl (ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
	
	@Override
	public void insertIntoJoinedTable(Integer listing_id, Integer image_id) {
		imageRepository.insertIntoJoinedTable(listing_id, image_id);
	}

	@Override
	public List<ImageRoute> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Optional<ImageRoute> findById(int theId) {
		return imageRepository.findById(theId);
	}
	
	@Override
	public Optional<ImageRoute> findByImageRoute(String imageRoute){
		return imageRepository.findByImageRoute(imageRoute);
	}
	
	@Override
	public List<Integer> findByListingIdInJoinedTable (int listingId){
		return imageRepository.findByListingIdInJoinedTable(listingId);
	}

	@Override
	public void save(ImageRoute image) {
		imageRepository.save(image);
	}

	@Override
	public void deleteById(int theId) {
		imageRepository.deleteById(theId);
	}
	

	

}
