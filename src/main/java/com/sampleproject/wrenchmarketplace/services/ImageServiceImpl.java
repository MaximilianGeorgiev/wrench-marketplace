package com.sampleproject.wrenchmarketplace.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sampleproject.wrenchmarketplace.dao.ImageRepository;
import com.sampleproject.wrenchmarketplace.entities.ImageRoute;


@Service
public class ImageServiceImpl implements ImageService {
	private ImageRepository imageRepository;
	
	public ImageServiceImpl (ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
	
	/*
	 * Stores the file physically in /resources/uploads Since a lot of browsers have
	 * blocked loading resources from local files, the whole uploads directory has
	 * been uploaded to a server (Web Server for Chrome extention). The file is
	 * first saved with the absolute path to this folder but in the database the
	 * server path is stored instead
	 * 
	 * Requires reconfiguring if someone else clones this project
	 */

	public String handleFileUpload(MultipartFile file) {
		String absolutePathToUploads = "C://Repositories//wrench-marketplace//wrench-marketplace//src//main//resources//uploads//";
		String filePath = absolutePathToUploads + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

		/* current time milis in order to avoid duplicate names */
		String serverPathToUploads = "http://127.0.0.1:8033/";
		String filePathOnServer = serverPathToUploads + System.currentTimeMillis() + "_" + file.getOriginalFilename();
		
		/* if file is already uploaded cease upload */
		List<ImageRoute> routes = this.findAll();
		
		for (ImageRoute route : routes) {
			if (route.getImageRoute().endsWith(file.getOriginalFilename())) {
				return null;
			}
		}

		try {
			String fileExtension = file.getOriginalFilename()
					.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			
			/* Need to find a way to display that the format is incorrect. */
			if ((!fileExtension.equals("png")) && (!fileExtension.equals("jpg")
									&& (!fileExtension.equals("jpeg")
										&&	(!fileExtension.equals("bmp"))))) {
				return null;
			}
			

			File dest = new File(filePath);
			file.transferTo(dest);
			save(new ImageRoute(filePathOnServer));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePathOnServer;
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
