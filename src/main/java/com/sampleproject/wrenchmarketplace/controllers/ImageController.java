package com.sampleproject.wrenchmarketplace.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.sampleproject.wrenchmarketplace.entities.ImageRoute;
import com.sampleproject.wrenchmarketplace.services.ImageService;

@Controller
public class ImageController {
	private ImageService imageService;

	public ImageController(ImageService imageService) {
		this.imageService = imageService;
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

		//current time milis in order to avoid duplicate names
		String serverPathToUploads = "http://127.0.0.1:8033/";
		String filePathOnServer = serverPathToUploads + System.currentTimeMillis() + "_" + file.getOriginalFilename();
		
		try {
			File dest = new File(filePath);
			file.transferTo(dest);
			imageService.save(new ImageRoute(0, filePathOnServer));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return filePathOnServer;
	}
}
